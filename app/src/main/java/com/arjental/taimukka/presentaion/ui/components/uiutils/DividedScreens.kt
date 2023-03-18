package com.arjental.taimukka.presentaion.ui.components.uiutils

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * Describes left stack of screen, right stack of screen
 * and full stack through all screen if this elements exists.
 */

interface DividedScreens : java.io.Serializable {
    val left: ImmutableList<ScreenPart>
    val right: ImmutableList<ScreenPart>
    val full: ImmutableList<ScreenPart>

    /**
     * Used to change [ScreenPart.onElevated] field, in this method you have to found exact [ScreenPart]
     * and mutate [DividedScreens] state object
     */
    fun elevated(screenPart: ScreenPart, foundInRight: Boolean = true): DividedScreens

    /**
     * Helps to fast inverse elevation, **better not to override**
     */
    fun ImmutableList<ScreenPart>.inverseElevated(screenPart: ScreenPart): ImmutableList<ScreenPart> {
        return right.map {
            if (it == screenPart) it.inverseElevated() else it
        }.toImmutableList()
    }

}
