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
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arjental.taimukka.ui.screens.main.MainScreen
import com.arjental.taimukka.ui.theme.TaimukkaTheme
import dagger.android.support.DaggerAppCompatActivity

class TaimukkaActivity : DaggerAppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)






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



}



