package com.arjental.taimukka.domain.repos

import com.arjental.taimukka.data.cash.holders.ApplicationsStatsHolder
import com.arjental.taimukka.data.cash.holders.UpdateTimesHolder
import com.arjental.taimukka.data.stats.UsageStatsManager
import com.arjental.taimukka.entities.data.cash.toDomain
import com.arjental.taimukka.entities.data.user_stats.toDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.domain.stats.toCash
import com.arjental.taimukka.other.utils.resource.Resource
import com.arjental.taimukka.other.utils.time.TimeUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface UserStatsRepository {
    suspend fun applicationsStats(): Flow<Resource<List<LaunchedAppDomain>>>
}

class UserStatsRepositoryImpl @Inject constructor(
    private val usageStatsManager: UsageStatsManager,
    private val updateTimesHolder: UpdateTimesHolder,
    private val applicationsStatsHolder: ApplicationsStatsHolder
): UserStatsRepository {

    override suspend fun applicationsStats(): Flow<Resource<List<LaunchedAppDomain>>> = channelFlow {
        send(Resource.loading())
        //get from cash
        val cashedUpdatedApps = async { applicationsStatsHolder.getApplications().toDomain() }
        val lastUpdateAppsTime = async { updateTimesHolder.getUpdatedAppList() }
        val currentTime = TimeUtil.getCurrentTimeMillis()
        //send from cash
        send(Resource.loading(data = cashedUpdatedApps.await()))
        //get from stats
        val stats = usageStatsManager.getApplicationsStats(startTimestamp = lastUpdateAppsTime.await(), finishTimestamp = currentTime).toDomain()
        //merge
        val mergedElements = merge(cash = cashedUpdatedApps.await(), stats = stats.associateBy { it.appPackage }.toMutableMap())
        //send merged
        launch { send(Resource.success(data = mergedElements)) }
        launch { applicationsStatsHolder.setApplications(applicationsList = mergedElements.toCash()) }
    }

    //stats - package name + domain
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


}

