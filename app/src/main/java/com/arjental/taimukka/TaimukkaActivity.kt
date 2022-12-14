package com.arjental.taimukka

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process.myUid
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.ui.screens.main.MainScreen
import com.arjental.taimukka.ui.theme.TaimukkaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaimukkaActivity : ComponentActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ( checkUsageStatsPermission() ) {

            val usageStatsManager = this.getSystemService("usagestats") as UsageStatsManager
            var foregroundAppPackageName : String? = null
            val currentTime = System.currentTimeMillis()
            val usageEvents = usageStatsManager.queryEvents( 0 , currentTime )
            val usageEvent = UsageEvents.Event()
            while ( usageEvents.hasNextEvent() ) {
                usageEvents.getNextEvent( usageEvent )
                Log.d( "APP" , "${usageEvent.packageName} ${usageEvent.timeStamp}" )
            }
        } else {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }




//        lifecycleScope.launch (Dispatchers.Main) {
//            usm.launch()
//        }

        setContent {
            TaimukkaTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen("Android")
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun checkUsageStatsPermission(): Boolean {
        val appOpsManager = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow("android:get_usage_stats", myUid(), packageName)
        } else {
            appOpsManager.checkOpNoThrow("android:get_usage_stats", myUid(), packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

}



