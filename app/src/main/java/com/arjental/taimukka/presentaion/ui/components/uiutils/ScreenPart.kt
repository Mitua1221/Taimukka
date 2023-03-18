package com.arjental.taimukka.presentaion.ui.components.uiutils

/**
 * Provides part of screen for tablets, foldables and phones
 * @param onElevate means that if screen in second column on fablets and on whole screen on phones
 * @param canDuplicate means that screen can open 3 or 4 time in one screen, making stack
 */
@kotlinx.serialization.Serializable
open class ScreenPart(
    /**
     * [onElevate] means that if screen in second column on fablets and on whole screen on phones
     */
    open val onElevated: Boolean = false,
    /**
     * [canDuplicate] means that screen can open 3 or 4 time in one screen, making stack
     */
    open val canDuplicate: Boolean = false
) : TScreen() {

    /**
     * Inverse [onElevated] flag
     */
    open fun inverseElevated(): ScreenPart = ScreenPart(onElevated = !onElevated)


}