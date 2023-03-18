package com.arjental.taimukka.presentaion.ui.components.navigations

import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.presentaion.ui.components.uiutils.DividedScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart


/**
 * Function for navigation
 *
 * If screen not showing, it will change [ScreenPart.onElevated] and screen will show after included
 * state modification. [ScreenPart.onElevated] always inverted, so on second call of this function, screen will disappear.
 *
 * @param vm is [TViewModel] with [DividedScreens] as state
 * @param changeScreen is [ScreenPart] that we want to show/hide
 */

fun <State : DividedScreens, Effect : Any> wNav(
    vm: TViewModel<State, Effect>,
    changeScreen: ScreenPart
) {

    //modify vm state
    vm.modifyState {
        @Suppress("UNCHECKED_CAST")
        //invert elevation
        it.elevated(screenPart = changeScreen) as State
    }
}

