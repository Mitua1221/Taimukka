package com.arjental.taimukka.repos.user_stats_manager

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


interface UsageStatsManager {

    suspend fun launch(context: Context)

}

class UsageStatsManagerImpl @Inject constructor() : UsageStatsManager {

    @SuppressLint("WrongConstant")
    override suspend fun launch(context: Context) {
        if (checkUsageStatsPermission(context = context)) {
            val usageStatsManager = context.getSystemService("usagestats") as android.app.usage.UsageStatsManager
            var foregroundAppPackageName: String? = null
            val currentTime = System.currentTimeMillis()
            val usageEvents = usageStatsManager.queryEvents(0, currentTime)
            val usageEvent = UsageEvents.Event()
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(usageEvent)
                Log.d("APP", "${usageEvent.packageName} ${usageEvent.timeStamp}")
            }
        } else {
            context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
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

    private fun getAllAppInfo(context: Context, packageName: String): String? {
        return try {
            val packageManager = context.packageManager
            val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            packageManager.getApplicationLabel(info) as String
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

}