package com.arjental.taimukka

import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.arjental.taimukka.presentaion.ui.screens.app_list.AppListTab
import com.arjental.taimukka.presentaion.ui.screens.main.MainTab
import com.arjental.taimukka.presentaion.ui.theme.TaimukkaTheme
import com.arjental.taimukka.other.utils.components.activity.TaimukkaDaggerActivity
import com.arjental.taimukka.other.utils.factories.viewmodel.Inject
import com.arjental.taimukka.presentaion.ui.components.app.TaimukkaApplication
import com.google.accompanist.adaptive.calculateDisplayFeatures


class TaimukkaActivity : TaimukkaDaggerActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightNavigationBars = true

        setContent {
            TaimukkaTheme {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)
                Inject(viewModelFactory) {
                    TaimukkaApplication(
                        windowSize = windowSize,
                        displayFeatures = displayFeatures,
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun ReplyAppPreview() {
    TaimukkaTheme {
        TaimukkaApplication(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 700, heightDp = 500)
@Composable
fun ReplyAppPreviewTablet() {
    TaimukkaTheme {
        TaimukkaApplication(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(700.dp, 500.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 500, heightDp = 700)
@Composable
fun ReplyAppPreviewTabletPortrait() {
    TaimukkaTheme {
        TaimukkaApplication(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(500.dp, 700.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1100, heightDp = 600)
@Composable
fun ReplyAppPreviewDesktop() {
    TaimukkaTheme {
        TaimukkaApplication(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(1100.dp, 600.dp)),
            displayFeatures = emptyList(),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
@Composable
fun ReplyAppPreviewDesktopPortrait() {
    TaimukkaTheme {
        TaimukkaApplication(
            windowSize = WindowSizeClass.calculateFromSize(DpSize(600.dp, 1100.dp)),
            displayFeatures = emptyList(),
        )
    }
}



