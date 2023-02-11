package com.arjental.taimukka.data.stats

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Process
import com.arjental.taimukka.entities.data.user_stats.AppInformation
import com.arjental.taimukka.entities.data.user_stats.LaunchedApp
import com.arjental.taimukka.other.utils.annotataions.Category
import dagger.android.support.DaggerAppCompatActivity
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

        var previousUsageEvent: UsageEvents.Event? = null

        while (usageEvents.hasNextEvent()) {

            val usageEvent = UsageEvents.Event()
            usageEvents.getNextEvent(usageEvent)

            if (previousUsageEvent != null) {
                combinePreviousUsage(
                    usageEventTimeStamp = usageEvent.timeStamp,
                    map = map,
                    previousUsageEvent = previousUsageEvent,
                    appInfo = appInfo[previousUsageEvent.packageName]
                )
            }

            previousUsageEvent = usageEvent
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
        val previousUsageEventPackageName = previousUsageEvent.packageName

        if (map.containsKey(previousUsageEventPackageName)) {
            map[previousUsageEventPackageName]?.launches?.add(Pair(first = previousUsageEvent.timeStamp, second = usageEventTimeStamp))
        } else {
            map[previousUsageEventPackageName] = LaunchedApp(
                appPackage = previousUsageEventPackageName,
                appName = appInfo?.name ?: "-",
                launches = mutableListOf(Pair(first = previousUsageEvent.timeStamp, second = usageEventTimeStamp)),
                nonSystem = !(appInfo?.isAppSystem ?: true),
                appCategory = appInfo?.appCategory
            )
        }
    }

    private fun checkUsageStatsPermission(context: Context): Boolean {
        val appOpsManager = context.getSystemService(DaggerAppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow("android:get_usage_stats", Process.myUid(), context.packageName)
        } else {
            appOpsManager.checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }


////    @Suppress("DEPRECATION")
//    fun isSystemApp(packageName: String): Boolean {
//        return try {
//            // Get packageinfo for target application
//            val targetPkgInfo: PackageInfo = context.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//            // Get packageinfo for system package
//            val sys: PackageInfo = context.packageManager.getPackageInfo("android", PackageManager.GET_SIGNATURES)
//            // Match both packageinfo for there signatures
//            targetPkgInfo.signatures != null && (sys.signatures[0] == targetPkgInfo.signatures[0])
//        } catch (e: NameNotFoundException) {
//            false
//        }
//    }

//    /**
//     * Parse events of launch/stop apps, launch/stop screen
//     * @param context additional to [CoroutineScope.coroutineContext] context of the coroutine.
//     * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
//     */
//
//    private suspend fun combineUserEvents(usageEvents: UsageEvents, currentTime: Long, nonSystemAppPackages: Set<String>): CombinedUserEvents {
//        val map = mutableMapOf<String, LaunchedApp>()
//        val interactive = mutableListOf<Pair<Int, Long>>()
//
//        var previousUsageEvent: UsageEvents.Event? = null
//
//        while (usageEvents.hasNextEvent()) {
//
//            val usageEvent = UsageEvents.Event()
//            usageEvents.getNextEvent(usageEvent)
//
//            if (usageEvent.eventType == UsageEvents.Event.SCREEN_INTERACTIVE ||
//                usageEvent.eventType == UsageEvents.Event.SCREEN_NON_INTERACTIVE ||
//                usageEvent.eventType == UsageEvents.Event.DEVICE_SHUTDOWN
//            ) {
//                interactive.add(usageEvent.eventType to usageEvent.timeStamp)
//            }
//
//            if (previousUsageEvent != null) {
//                combinePreviousUsage(
//                    usageEventTimeStamp = usageEvent.timeStamp,
//                    map = map,
//                    previousUsageEvent = previousUsageEvent,
//                    nonSystemApp = nonSystemAppPackages.contains(previousUsageEvent.packageName),
//                    appInfo = appInfo[previousUsageEvent.packageName],
//                )
//            }
//
//            previousUsageEvent = usageEvent
//
//        }
//
//        if (previousUsageEvent != null) {
//            combinePreviousUsage(
//                usageEventTimeStamp = currentTime,
//                map = map,
//                previousUsageEvent = previousUsageEvent,
//                nonSystemApp = nonSystemAppPackages.contains(previousUsageEvent.packageName),
//                appInfo = appInfo[previousUsageEvent.packageName]
//            )
//        }
//
//        return CombinedUserEvents(
//            launchedAppsMap = map,
//            screenInteractiveEvents = interactive,
//        )
//    }


}