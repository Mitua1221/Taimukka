package com.arjental.taimukka.domain.repos

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
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject


interface UsageStatsManager {

    suspend fun launch(startTimestamp: Long = 0L)

}

class UsageStatsManagerImpl @Inject constructor(
    private val context: Context
) : UsageStatsManager {

    private val packageManager: PackageManager = context.packageManager

    @SuppressLint("WrongConstant")
    override suspend fun launch(startTimestamp: Long): Unit = withContext(Dispatchers.Default) {
        if (checkUsageStatsPermission(context = context)) {
            val usageStatsManager = context.getSystemService("usagestats") as android.app.usage.UsageStatsManager
            val currentTime = Clock.System.now().toEpochMilliseconds()

            val usageEvents = async { usageStatsManager.queryEvents(startTimestamp, currentTime) }
            val nonSystemAppPackages = async { getNonSystemAppsList(context = context) }

            val combinedUsers = async { combineUserEvents(usageEvents = usageEvents.await(), currentTime = currentTime, nonSystemAppPackages = nonSystemAppPackages.await()) }

            combinedUsers.await().forEach {
                it.value.launches.sumOf { it.second - it.first }
            }


        } else {
            withContext(Dispatchers.Main) { context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)) }
        }
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
            e.printStackTrace()
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

    private suspend fun combineUserEvents(usageEvents: UsageEvents, currentTime: Long, nonSystemAppPackages: Set<String>): Map<String, LaunchedApp> {
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

        return map
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

    private class AppInformation(
        val name: String = "-"
    )

    private class LaunchedApp(
        val appPackage: String,
        val appName: String,
        val nonSystem: Boolean,
        val launches: MutableList<Pair<Long, Long>>,
    )

}