package com.arjental.taimukka.presentaion.ui.components.uiutils

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.collections.immutable.ImmutableList

/**
 * Describes left stack of screen, right stack of screen
 * and full stack through all screen if this elements exists.
 */

interface DividedScreens : java.io.Serializable{
    val left: ImmutableList<ScreenPart>
    val right: ImmutableList<ScreenPart>
    val full: ImmutableList<ScreenPart>
}
