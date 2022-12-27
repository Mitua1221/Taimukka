package com.arjental.taimukka.data.stats

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Process
import android.provider.Settings
import com.arjental.taimukka.entities.data.user_stats.AppInformation
import com.arjental.taimukka.entities.data.user_stats.CombinedUserEvents
import com.arjental.taimukka.entities.data.user_stats.LaunchedApp
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import javax.inject.Inject


interface UsageStatsManager {

    suspend fun launch(startTimestamp: Long = 0L): CombinedUserEvents

}

class UsageStatsManagerImpl @Inject constructor(
    private val context: Context
) : UsageStatsManager {

    private val packageManager: PackageManager = context.packageManager


    @SuppressLint("WrongConstant")
    override suspend fun launch(startTimestamp: Long): CombinedUserEvents = withContext(Dispatchers.Default) {
        //if (checkUsageStatsPermission(context = context)) {
            val usageStatsManager = context.getSystemService("usagestats") as android.app.usage.UsageStatsManager
            val currentTime = Clock.System.now().toEpochMilliseconds()

            val usageEvents = async { usageStatsManager.queryEvents(startTimestamp, currentTime) }
            val nonSystemAppPackages = async { getNonSystemAppsList(context = context) }

            return@withContext combineUserEvents(usageEvents = usageEvents.await(), currentTime = currentTime, nonSystemAppPackages = nonSystemAppPackages.await())



//        } else {
//            withContext(Dispatchers.Main) { context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)) }
//        }
    }

    @Suppress("DEPRECATION")
    private fun checkUsageStatsPermission(context: Context): Boolean {
        val appOpsManager = context.getSystemService(DaggerAppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow("android:get_usage_stats", Process.myUid(), context.packageName)
        } else {
            appOpsManager.checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private suspend inline fun getAllAppInfo(packageName: String): AppInformation {
        return try {
            val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            AppInformation(name = packageManager.getApplicationLabel(info) as String)
        } catch (e: NameNotFoundException) {
            //e.printStackTrace()
            AppInformation()
        }
    }

    @Suppress("DEPRECATION")
    private suspend fun getNonSystemAppsList(context: Context): Set<String> {
        val packageManager = context.packageManager
        val appInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appInfoMap = mutableSetOf<String>()
        for (appInfo in appInfos) {
            if (appInfo.flags != ApplicationInfo.FLAG_SYSTEM) appInfoMap.add(appInfo.packageName)
        }
        return appInfoMap
    }

    /**
     * Parse events of launch/stop apps, launch/stop screen
     * @param context additional to [CoroutineScope.coroutineContext] context of the coroutine.
     * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
     */

    private suspend fun combineUserEvents(usageEvents: UsageEvents, currentTime: Long, nonSystemAppPackages: Set<String>): CombinedUserEvents {
        val map = mutableMapOf<String, LaunchedApp>()
        val interactive = mutableListOf<Pair<Int, Long>>()

        var previousUsageEvent: UsageEvents.Event? = null

        while (usageEvents.hasNextEvent()) {

            val usageEvent = UsageEvents.Event()
            usageEvents.getNextEvent(usageEvent)

            if (usageEvent.eventType == UsageEvents.Event.SCREEN_INTERACTIVE ||
                usageEvent.eventType == UsageEvents.Event.SCREEN_NON_INTERACTIVE ||
                usageEvent.eventType == UsageEvents.Event.DEVICE_SHUTDOWN
            ) {
                interactive.add(usageEvent.eventType to usageEvent.timeStamp)
            }

            if (previousUsageEvent != null) {
                combinePreviousUsage(
                    usageEventTimeStamp = usageEvent.timeStamp,
                    map = map,
                    previousUsageEvent = previousUsageEvent,
                    nonSystemApp = nonSystemAppPackages.contains(previousUsageEvent.packageName),
                )
            }

            previousUsageEvent = usageEvent

        }

        if (previousUsageEvent != null) {
            combinePreviousUsage(
                usageEventTimeStamp = currentTime,
                map = map,
                previousUsageEvent = previousUsageEvent,
                nonSystemApp = nonSystemAppPackages.contains(previousUsageEvent.packageName)
            )
        }

        return CombinedUserEvents(
            launchedAppsMap = map,
            screenInteractiveEvents = interactive,
        )
    }

    private suspend inline fun combinePreviousUsage(usageEventTimeStamp: Long, map: MutableMap<String, LaunchedApp>, previousUsageEvent: UsageEvents.Event, nonSystemApp: Boolean) {
        val previousUsageEventPackageName = previousUsageEvent.packageName

        if (map.containsKey(previousUsageEventPackageName)) {
            map[previousUsageEventPackageName]?.launches?.add(Pair(first = previousUsageEvent.timeStamp, second = usageEventTimeStamp))
        } else {
            val applicationInfo = getAllAppInfo(previousUsageEventPackageName)
            map[previousUsageEventPackageName] = LaunchedApp(appPackage = previousUsageEventPackageName, appName = applicationInfo.name, launches = mutableListOf(Pair(first = previousUsageEvent.timeStamp, second = usageEventTimeStamp)), nonSystem = nonSystemApp)
        }
    }

}