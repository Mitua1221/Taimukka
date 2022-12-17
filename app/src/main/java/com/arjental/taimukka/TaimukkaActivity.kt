package com.arjental.taimukka

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arjental.taimukka.ui.screens.main.MainScreen
import com.arjental.taimukka.ui.theme.TaimukkaTheme
import com.arjental.taimukka.utils.components.activity.TaimukkaDaggerActivity
import com.arjental.taimukka.utils.factories.viewmodel.Inject

class TaimukkaActivity : TaimukkaDaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaimukkaTheme {
                Inject(viewModelFactory) {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        MainScreen("Android")
                    }
                }
            }
        }
    }


}



