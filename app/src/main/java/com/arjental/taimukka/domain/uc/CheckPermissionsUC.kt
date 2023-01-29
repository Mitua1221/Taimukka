package com.arjental.taimukka.domain.uc

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process
import android.provider.Settings
import androidx.annotation.StringRes
import com.arjental.taimukka.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.serialization.Serializable
import javax.inject.Inject

class CheckPermissionsUC @Inject constructor(
    private val context: Context
) {

    suspend fun checkSplashPermissions(): Map<TPermission, Boolean> {
        val needToRequestPermissions = mutableMapOf<TPermission, Boolean>()
        if (!hasUsageStatsPermission()) needToRequestPermissions.put(TPermission.CHECK_USAGE_STATS(), true)
        return needToRequestPermissions
    }

    private suspend fun hasUsageStatsPermission(): Boolean {
        val appOpsManager = context.getSystemService(DaggerAppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow("android:get_usage_stats", Process.myUid(), context.packageName)
        } else {
            @Suppress("DEPRECATION")
            appOpsManager.checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

}

@Serializable
sealed class TPermission(
    val mandatory: Boolean,
    val system: Boolean,
    @StringRes val description: Int,
) {
    abstract fun requestSystemPermission(activityContext: Context)
    @Serializable
    class CHECK_USAGE_STATS : TPermission(mandatory = true, system = true, description = R.string.permission_ucm_desc) {
        override fun requestSystemPermission(activityContext: Context) {
            activityContext.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }
}