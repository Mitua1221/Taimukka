package com.arjental.taimukka.presentaion.ui.components.loading

import androidx.compose.runtime.Composable
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart

/**
 * Use for show loading bar in some screen part of applicaation
 */
class LoadingPart(
    override val onElevated: Boolean = false,
) : ScreenPart() {

    override val implementParentLifecycle: Boolean = false

    @Composable
    override fun TContent() {
        TCircularProgressIndicator()
    }

    override fun inverseElevated(): ScreenPart = LoadingPart(onElevated = !onElevated)

}


