package com.arjental.taimukka.presentaion.ui.components.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalComponentType
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalDisplayFeatures
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane


@Composable
fun TWrapLines(
    modifier: Modifier = Modifier,
    firstColumn: @Composable () -> Unit,
    secondColumn: (@Composable () -> Unit)? = null,
) {

    val emailLazyListState = rememberLazyListState()

    if (LocalComponentType.current == ContentType.DUAL_PANE) {
        TwoPane(
            first = {
                firstColumn()
            },
            second = {
                secondColumn?.invoke()
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = LocalDisplayFeatures.current
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            firstColumn()
        }
    }

}