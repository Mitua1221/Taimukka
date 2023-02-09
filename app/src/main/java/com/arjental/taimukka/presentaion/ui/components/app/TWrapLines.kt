package com.arjental.taimukka.presentaion.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalDisplayFeatures
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.screens.empty.EmptyScreenContent
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun TWrapLines(
    modifier: Modifier = Modifier,
    firstColumnScreens: ImmutableList<ScreenPart> = persistentListOf(),
    secondColumnScreens: ImmutableList<ScreenPart> = persistentListOf(),
    firstColumnNative: @Composable () -> Unit = { EmptyScreenContent() },
    secondColumnNative: @Composable () -> Unit = { EmptyScreenContent() },
    splitFraction: Float = 0.4f
) {

    if (isDual()) {
        TwoPane(
            modifier = modifier.fillMaxSize(),
            first = {
                firstColumnScreens.firstOrNull()?.let { screen ->
                    Navigator(screen = screen)
                } ?: firstColumnNative()
            },
            second = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .then(
                            when {
                                isDrawerNav() -> Modifier.padding(top = 16.dp)
                                else -> Modifier
                            }
                        )
                        .then(
                            when {
                                isDrawerNav() -> Modifier.padding(end = 16.dp)
                                else -> Modifier.padding(end = 8.dp)
                            }
                        )
                        .clip(shape = MaterialTheme.shapes.extraLarge)
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    secondColumnScreens.firstOrNull()?.let { screen ->
                        Navigator(screen = screen)
                    } ?: firstColumnNative()
                }
            },
            strategy = HorizontalTwoPaneStrategy(
                splitFraction = splitFraction, gapWidth =
                when {
                    isDrawerNav() -> 24.dp
                    isRailNav() -> 16.dp
                    else -> 0.dp
                }
            ),
            displayFeatures = LocalDisplayFeatures.current
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            firstColumnScreens.firstOrNull()?.let { screen ->
                Navigator(screen = screen)
            } ?: firstColumnNative()
        }
    }

}