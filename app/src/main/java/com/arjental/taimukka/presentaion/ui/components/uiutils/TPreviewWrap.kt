package com.arjental.taimukka.presentaion.ui.components.uiutils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arjental.taimukka.TaimukkaActivity
import com.arjental.taimukka.presentaion.ui.components.app.*
import com.arjental.taimukka.presentaion.ui.theme.TaimukkaTheme
import com.google.accompanist.adaptive.calculateDisplayFeatures

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TPreviewWrap(
    contentType: ContentType = ContentType.SINGLE_PANE,
    navigationType: NavigationType = NavigationType.BOTTOM_NAVIGATION,
    navigationContentPosition: NavigationContentPosition = NavigationContentPosition.TOP,
    content: @Composable () -> Unit,
) {
    val activity = TaimukkaActivity()
    CompositionLocalProvider(
        LocalTActivity provides activity,
    ) {
        TaimukkaTheme {
            val windowSize = calculateWindowSizeClass(activity)
            val displayFeatures = calculateDisplayFeatures(activity)
            CompositionLocalProvider(
                LocalDisplayFeatures provides displayFeatures,
                LocalContentType provides contentType,
                LocalNavigationType provides navigationType,
                LocalNavigationContentPosition provides navigationContentPosition
            ) {
                content()
            }
        }
    }


}