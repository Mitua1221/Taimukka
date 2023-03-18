package com.arjental.taimukka.data.stats

import android.annotation.SuppressLint
import android.app.usage.UsageEvents
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import com.arjental.taimukka.entities.data.user_stats.AppInformation
import com.arjental.taimukka.entities.data.user_stats.LaunchedApp
import com.arjental.taimukka.entities.pierce.NOTIFICATION_INTERRUPTION
import com.arjental.taimukka.entities.pierce.NOTIFICATION_SEEN
import com.arjental.taimukka.entities.pierce.notification_type.NOTIFICATION
import com.arjental.taimukka.other.utils.annotataions.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface UsageStatsManager {
    suspend fun getApplicationsStats(startTimestamp: Long, finishTimestamp: Long): List<LaunchedApp>
}

//https://medium.com/@quiro91/show-app-usage-with-usagestatsmanager-d47294537dab
//https://proandroiddev.com/accessing-app-usage-history-in-android-79c3af861ccf

class UsageStatsManagerImpl @Inject constructor(
    private val context: Context
) : UsageStatsManager {

    private val packageManager: PackageManager by lazy { context.packageManager }

    @SuppressLint("WrongConstant")
    override suspend fun getApplicationsStats(startTimestamp: Long, finishTimestamp: Long): List<LaunchedApp> = withContext(Dispatchers.Default) {
        val usageStatsManager = context.getSystemService("usagestats") as android.app.usage.UsageStatsManager

        val appInfo = getInstalledApplications() //map packageName + AppInfo

        val usageEvents = usageStatsManager.queryEvents(startTimestamp, finishTimestamp)

        return@withContext combineApplicationStats(usageEvents = usageEvents, appInfo = appInfo)
    }

    private suspend fun getInstalledApplications(): Map<String, AppInformation> {
        val apps: List<ApplicationInfo> =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            }
        return apps.map { getAllAppInfo(it.packageName) }.associateBy { it.packageName }
    }

    private suspend inline fun getAllAppInfo(packageName: String): AppInformation {
        return try {
            val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(packageName, 0)
            }
            val isAppSystem = isApplicationSystem(info)
            val applicationCategory = getApplicationCategory(info)
            AppInformation(name = packageManager.getApplicationLabel(info.applicationInfo) as String, packageName = packageName, isAppSystem = isAppSystem, appCategory = applicationCategory)
        } catch (e: NameNotFoundException) {
            AppInformation(packageName = packageName, isAppSystem = true, appCategory = null)
        }
    }

    /**
     * Returns application category marked [Category], but android provides it only on api >= 26,
     * on lower models category unknown
     */

    private suspend fun getApplicationCategory(info: PackageInfo): @Category Int? {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            info.applicationInfo.category
        } else null
    }

    private suspend fun combineApplicationStats(usageEvents: UsageEvents, appInfo: Map<String, AppInformation>): List<LaunchedApp> {

        val map = mutableMapOf<String, LaunchedApp>()

        //count screen launches events -> args is package name, activity class name to [UsageEvents]
        val applicationScreenLaunches = mutableMapOf<String, MutableMap<String, UsageEvents.Event>>()

        //count screen launches events -> args is package name, activity class name to [UsageEvents]
        val applicationLaunches = mutableMapOf<String, MutableMap<String, UsageEvents.Event>>()

        while (usageEvents.hasNextEvent()) {

            val usageEvent = UsageEvents.Event()
            usageEvents.getNextEvent(usageEvent)

            //count events type of screen show/hide
            if (usageEvent.eventType == UsageEvents.Event.ACTIVITY_RESUMED || //android 7.0 resume/stop problem
                usageEvent.eventType == UsageEvents.Event.ACTIVITY_PAUSED) {
                if (usageEvent.eventType == UsageEvents.Event.ACTIVITY_PAUSED) { //if previous was resumed state
                    val previousUsageEvent = applicationScreenLaunches[usageEvent.packageName]?.get(usageEvent.className)
                    if (previousUsageEvent != null) {
                        combinePreviousUsage(
                            usageEventTimeStamp = usageEvent.timeStamp,
                            map = map,
                            previousUsageEvent = previousUsageEvent,
                            appInfo = appInfo[previousUsageEvent.packageName]
                        )
                    }
                } else { //if application screens launches
                    val packageLaunchesMap = applicationScreenLaunches[usageEvent.packageName] ?: mutableMapOf()
                    packageLaunchesMap[usageEvent.className] = usageEvent
                    applicationScreenLaunches[usageEvent.packageName] = packageLaunchesMap
                }
            }

            //check notifications
            if (usageEvent.eventType == NOTIFICATION_INTERRUPTION) {
                //add notifications event to list
                notificationInterrupted(
                    applicationsMap = map,
                    usageEvent = usageEvent,
                    appInfo = appInfo[usageEvent.packageName],
                )
            }

            if (usageEvent.eventType == NOTIFICATION_SEEN) {
                //add notifications event to list
                notificationInterrupted(
                    applicationsMap = map,
                    usageEvent = usageEvent,
                    appInfo = appInfo[usageEvent.packageName],
                    notificationType = NOTIFICATION.SEEN
                )
            }

            if (usageEvent.eventType == NOTIFICATION_SEEN) {
                //add notifications event to list
                notificationInterrupted(
                    applicationsMap = map,
                    usageEvent = usageEvent,
                    appInfo = appInfo[usageEvent.packageName],
                    notificationType = NOTIFICATION.SEEN
                )
            }

        }
        return map.values.toList()
    }

    private suspend fun isApplicationSystem(info: PackageInfo): Boolean {
        val appInfo = info.applicationInfo
        return (appInfo.flags != ApplicationInfo.FLAG_SYSTEM && appInfo.flags != ApplicationInfo.FLAG_UPDATED_SYSTEM_APP && appInfo.sourceDir.startsWith("/data/app/"))
    }

    private suspend inline fun combinePreviousUsage(
        usageEventTimeStamp: Long,
        map: MutableMap<String, LaunchedApp>,
        previousUsageEvent: UsageEvents.Event,
        appInfo: AppInformation?, // if null, thia app is deleted
    ) {
        val previousUsageEventPackageName = previousUsageEvent.packageName ?: appInfo?.packageName

        if (previousUsageEventPackageName != null) {
            //check is it is already created
            map.checkApplicationExists(
                packageName = previousUsageEventPackageName,
                appInfo = appInfo
            )
            //add new element to it
            map[previousUsageEventPackageName]?.launches?.add(Pair(first = previousUsageEvent.timeStamp, second = usageEventTimeStamp))
        }
    }

    /**
     * Used to add notification event to map of receiver notification in selected timeline
     *
     * @param applicationsMap if [LaunchedApp] not exists in it, we have to create it
     * @param usageEvent is event
     * @param appInfo if null, this app is deleted
     * @param notificationType [NOTIFICATION.RECEIVED] for received, [NOTIFICATION.SEEN] for seen notifications
     */
    private suspend inline fun notificationInterrupted(
        applicationsMap: MutableMap<String, LaunchedApp>,
        usageEvent: UsageEvents.Event,
        appInfo: AppInformation?,
        notificationType: NOTIFICATION = NOTIFICATION.RECEIVED
    ) {
        val packageName = usageEvent.packageName ?: appInfo?.packageName

        if (packageName != null) {
            //check is it is already created
            applicationsMap.checkApplicationExists(
                packageName = packageName,
                appInfo = appInfo
            )
            //add to item, item exists
            when (notificationType) {
                NOTIFICATION.SEEN -> applicationsMap[packageName]?.notificationsReceived?.add(usageEvent.timeStamp)
                NOTIFICATION.RECEIVED -> applicationsMap[packageName]?.notificationsSeen?.add(usageEvent.timeStamp)
            }
        }
    }

    /**
     * Add [LaunchedApp] to map if it is not exists
     *
     * @param packageName
     * @param appInfo is infromation about app, if its null, app is deleted
     */
    private suspend inline fun MutableMap<String, LaunchedApp>.checkApplicationExists(
        packageName: String,
        appInfo: AppInformation?
    ) {
        if (!this.containsKey(packageName)) {
            this[packageName] = LaunchedApp(
                appPackage = packageName,
                appName = appInfo?.name ?: "-",
                launches = mutableListOf(),
                nonSystem = !(appInfo?.isAppSystem ?: true),
                appCategory = appInfo?.appCategory,
            )
        }
    }


}