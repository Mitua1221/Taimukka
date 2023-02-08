package com.arjental.taimukka

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.arjental.taimukka.other.utils.components.activity.TaimukkaDaggerActivity
import com.arjental.taimukka.other.utils.factories.viewmodel.SetViewModelFactory
import com.arjental.taimukka.presentaion.ui.components.app.TaimukkaApplication
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalDispatchers
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalTActivity
import com.arjental.taimukka.presentaion.ui.screens.splash.SplashVM
import com.arjental.taimukka.presentaion.ui.theme.TaimukkaTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures


class TaimukkaActivity : TaimukkaDaggerActivity() {

    private val splashValidateViewModel by viewModels<SplashVM> { viewModelFactory }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashValidateViewModel.ensureSplashActive()
            }
        }
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightNavigationBars = true

        setContent {
            CompositionLocalProvider(
                LocalTActivity provides this,
                LocalDispatchers provides dispatchers.get()
            ) {

                val themeState = splashValidateViewModel.colorsScheme().collectAsState()

                TaimukkaTheme(
                    darkTheme = if (themeState.value.isSystemThemeUsed) {
                        isSystemInDarkTheme()
                    } else {
                        themeState.value.isDarkThemeEnabled
                    }
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    val displayFeatures = calculateDisplayFeatures(this)
                    SetViewModelFactory(viewModelFactory) {
                        TaimukkaApplication(
                            windowSize = windowSize,
                            displayFeatures = displayFeatures,
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //bind service check permissions
    }

    override fun onPause() {
        super.onPause()
        //unbind service check permissions
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



