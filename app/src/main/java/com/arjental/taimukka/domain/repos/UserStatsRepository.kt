package com.arjental.taimukka.domain.repos

import com.arjental.taimukka.data.cash.holders.ApplicationsStatsHolder
import com.arjental.taimukka.data.cash.holders.UpdateTimesHolder
import com.arjental.taimukka.data.stats.UsageStatsManager
import com.arjental.taimukka.entities.data.cash.toDomain
import com.arjental.taimukka.entities.data.user_stats.toDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppTimeMarkDomain
import com.arjental.taimukka.entities.domain.stats.toCash
import com.arjental.taimukka.entities.pierce.SESSION_DEBOUNCE
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.pierce.timeline.TimelineType
import com.arjental.taimukka.entities.pierce.timeline.TimelineTypeMax
import com.arjental.taimukka.other.utils.percentage.divideToPercent
import com.arjental.taimukka.other.utils.resource.Resource
import com.arjental.taimukka.other.utils.time.TimeUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

interface UserStatsRepository {
    suspend fun applicationsStats(timeline: Timeline, selectionType: SelectionType): Flow<Resource<List<LaunchedAppDomain>>>

    /**
     * @param manager  holds real timestamp **|from|----|to|** in millis that we have to get from devices, because we dont have this data in cache
     * @param cache holds real timestamp **|from|----|to|** in millis that we have to get from cache, because this data already in cache
     */

    class MergedTimes(
        val manager: Select?,
        val cache: Select?,
    )

    /**
     * @param from timestamp in millis - start of selection
     * @param to timestamp in millis - end of selection
     */

    data class Select(
        val from: Long,
        val to: Long,
    )

}

class UserStatsRepositoryImpl @Inject constructor(
    private val usageStatsManager: UsageStatsManager,
    private val updateTimesHolder: UpdateTimesHolder,
    private val applicationsStatsHolder: ApplicationsStatsHolder
) : UserStatsRepository {

    //TODO we have to filter it in sql, but idk how to do that
    //TODO we lost active application timeline when its running
    //TODO merge activity periods if its less that a seconds between

    override suspend fun applicationsStats(timeline: Timeline, selectionType: SelectionType): Flow<Resource<List<LaunchedAppDomain>>> = channelFlow {
        send(Resource.loading())
        //Get cache updated times
        val cacheUpdateTimes = updateTimesHolder.getAppCacheTimestamps()

        val currentTime = TimeUtil.getCurrentTimeMillis()

        //count merge updates
        val lastUpdateAppsTime = mergeUpdatedTimes(
            current = currentTime,
            cashSelect = UserStatsRepository.Select(from = cacheUpdateTimes.first, to = cacheUpdateTimes.second),
            customSelect = if (timeline.from != null && timeline.to != null) UserStatsRepository.Select(to = timeline.to, from = timeline.from) else null,
            timelineType = timeline.timelineType
        )

        //get timeline part from cache
        val cashedStats = async {
            if (lastUpdateAppsTime.cache != null)
                applicationsStatsHolder.getApplications(from = lastUpdateAppsTime.cache.from, to = lastUpdateAppsTime.cache.to).toDomain()
            else
                null
        }
        //get timeline cache from device
        val managerStats = async {
            if (lastUpdateAppsTime.manager != null)
                usageStatsManager.getApplicationsStats(startTimestamp = lastUpdateAppsTime.manager.from, finishTimestamp = lastUpdateAppsTime.manager.to).filter { !it.nonSystem }.toDomain()
            else
                null
        }

        //immediate save changes to cache, it mustn't be cancellable
        if (managerStats.await() != null && lastUpdateAppsTime.manager != null) {
            launch(SupervisorJob()) {
                launch {
                    applicationsStatsHolder.setApplications(applicationsList = managerStats.await()!!.toCash())
                }
                launch {
                    updateTimesHolder.updatedAppList(from = lastUpdateAppsTime.manager.from, lastUpdateAppsTime.manager.to)
                }
            }
        }

        //merge
        val mergedElements = when {
            cashedStats.await() == null && managerStats.await() == null -> error("cashed info and user info cant be nullable both!")
            cashedStats.await() == null -> managerStats.await()!!
            managerStats.await() == null -> cashedStats.await()!!
            else -> merge(cash = cashedStats.await()!!, stats = managerStats.await()!!.associateBy { it.appPackage }.toMutableMap())
        }
        //count percentages and values
        val elementsWithPercents = when (selectionType) {
            SelectionType.SCREEN_TIME -> countTimelinesUsageInterest(mergedElements)
            SelectionType.NOTIFICATIONS -> countNotifications(mergedElements)
            SelectionType.SEANCES -> countSeances(mergedElements)
        }.sortedByDescending { it.realQuality }
        //paste
        send(Resource.success(data = elementsWithPercents))
    }

    /**
     * Combine cache information and recently information usages from device manager
     */
    private suspend fun merge(cash: List<LaunchedAppDomain>, stats: MutableMap<String, LaunchedAppDomain>): List<LaunchedAppDomain> {
        val newList: MutableList<LaunchedAppDomain> = cash.map {
            val newLaunches = it.launches.toMutableList()
            val launchedFromMap = stats[it.appPackage]
            newLaunches.addAll(launchedFromMap?.launches ?: emptyList())
            newLaunches.sortBy { launch -> launch.from }
            stats.remove(it.appPackage)
            it.copy(launches = newLaunches)
        }.toMutableList()
        newList.addAll(stats.values)
        return newList
    }

    /**
     * User can select predefined timeline, but part of it can be already parsed
     * so here we merging lastCashUpdateTimeline and lastTimeline from selected timeline
     * to combine them and get correct "from" long value that we can use
     *
     * And sometimes we have to clear cash, because selections can create a blank spaces in database
     * @param current current time
     * @param cashSelect cashed timeline 0/0 if no cache
     * @param customSelect custom selection if selected custom, otherwise null
     * @param timelineType is a type of selected timeline
     * @return [UserStatsRepository.MergedTimes] read about returned properties
     */

    private suspend fun mergeUpdatedTimes(
        current: Long = TimeUtil.getCurrentTimeMillis(),
        cashSelect: UserStatsRepository.Select,
        customSelect: UserStatsRepository.Select?,
        timelineType: TimelineType
    ): UserStatsRepository.MergedTimes {
        //if db only created
        if (cashSelect.from == 0L && cashSelect.to == 0L && timelineType != TimelineType.CUSTOM) {
            val last = TimeUtil.getPreviousTimeFromTimelineType(timelineType = TimelineTypeMax().max)
            return UserStatsRepository.MergedTimes(
                manager = UserStatsRepository.Select(from = last, to = current),//npe ok
                cache = null,
            )
        }

        //if something selected
        if (customSelect != null) {
            if (customSelect.from < cashSelect.from) {
                if (customSelect.to < cashSelect.from) {
                    return UserStatsRepository.MergedTimes(
                        manager = UserStatsRepository.Select(from = customSelect.from, to = cashSelect.from),
                        cache = null,
                    )
                } else {
                    return UserStatsRepository.MergedTimes(
                        manager = UserStatsRepository.Select(from = customSelect.from, to = cashSelect.from),
                        cache = UserStatsRepository.Select(from = cashSelect.from, to = customSelect.to),
                    )
                }
            } else {
                if (customSelect.to <= cashSelect.to) {
                    return UserStatsRepository.MergedTimes(
                        manager = null,
                        cache = UserStatsRepository.Select(from = customSelect.from, to = customSelect.to),
                    )
                } else {
                    return UserStatsRepository.MergedTimes(
                        manager = UserStatsRepository.Select(from = cashSelect.to, to = current),
                        cache = UserStatsRepository.Select(from = customSelect.from, to = cashSelect.to),
                    )
                }
            }
        }

        //selected only timeline
        if (timelineType != TimelineType.CUSTOM) {
            val fromCache = TimeUtil.getPreviousTimeFromTimelineType(timelineType = timelineType)
            return UserStatsRepository.MergedTimes(
                manager = UserStatsRepository.Select(from = cashSelect.to, to = current),
                cache = UserStatsRepository.Select(from = fromCache, to = cashSelect.to),
            )
        }
        error("no other way")
    }

    /**
     * Counts the timeline, gets the total number of milliseconds in the application for the period
     * accordingly calculates the total amount of time in the application for the period
     * calculates the percentage for each application
     * @return list not sorted
     */

    private suspend fun countTimelinesUsageInterest(applications: List<LaunchedAppDomain>): List<LaunchedAppDomain> = coroutineScope {
        val m = Mutex()
        var allApplicationRuntimeInMillis = 0L //all applications time
        //count only 1 app time
        val applicationRuntimeInMillis: (LaunchedAppDomain) -> Long = { app ->
            app.launches.sumOf { it.to - it.from }
        }
        return@coroutineScope applications.map { //in app list
            async {
                val appRuntime = applicationRuntimeInMillis(it)
                m.withLock { allApplicationRuntimeInMillis += appRuntime } //increment with sync
                it to appRuntime
            }
        }.map {
            it.await()
        }.map {
            val percentage = it.second.divideToPercent(allApplicationRuntimeInMillis)
            it.first.copy(percentage = percentage, realQuality = it.second, )
        }.sortedByDescending { it.realQuality }
    }

    /**
     * Count seances of application and its percentage defined on all launched applications.
     *
     * Seance means that activity not in foreground (after onPause) more than [SESSION_DEBOUNCE] seconds.
     */
    private suspend fun countSeances(applications: List<LaunchedAppDomain>): List<LaunchedAppDomain> = coroutineScope {
        val m = Mutex()
        var allApplicationsLaunchQuality = 0 //all applications launches
        //counting sessions
        val applicationSessionsQuality: (LaunchedAppDomain) -> Int = { app ->
            val sortedByLaunches = app.launches.sortedBy { it.from } //sort by from time
            var previousElement: LaunchedAppTimeMarkDomain? = null
            var applicationSessionQuality = 0
            sortedByLaunches.forEachIndexed { index, element ->
                if (previousElement != null) {
                    val millisBetweenSessions = element.from - previousElement!!.to
                    if (millisBetweenSessions > SESSION_DEBOUNCE) {
                        applicationSessionQuality++
                    }
                } else {
                    applicationSessionQuality++ //if first session
                }
                previousElement = element
            }
            applicationSessionQuality
        }
        return@coroutineScope applications.map { //in app list
            async {
                val applicationSessionsQuality = applicationSessionsQuality(it) // count session quality for app
                m.withLock { allApplicationsLaunchQuality += applicationSessionsQuality }//remember to know all applications launches quality
                it to applicationSessionsQuality
            }
        }.map {
            it.await()
        }.map {
            val percentage = it.second.divideToPercent(allApplicationsLaunchQuality)
            it.first.copy(percentage = percentage, realQuality = it.second.toLong(), )
        }
    }

    /**
     * Count notifications size in all applications and its common percentage.
     */
    private suspend fun countNotifications(applications: List<LaunchedAppDomain>): List<LaunchedAppDomain> {
        var allApplicationsNotifications = 0 //all notificationsQuality
        return applications.map { //in app list
            val notificationsSize = it.notificationsMarks.size
            allApplicationsNotifications += notificationsSize
            it to notificationsSize
        }.map {
            val percentage = it.second.divideToPercent(allApplicationsNotifications)
            it.first.copy(percentage = percentage, realQuality = it.second.toLong(), )
        }
    }

}

