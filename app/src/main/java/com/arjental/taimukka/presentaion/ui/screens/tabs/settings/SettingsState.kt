package com.arjental.taimukka.presentaion.ui.screens.tabs.settings

import androidx.annotation.StringRes
import com.arjental.taimukka.R
import com.arjental.taimukka.presentaion.ui.components.uiutils.DividedScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Transient


/**
 * All list element state - left, right, whole screen
 */
@kotlinx.serialization.Serializable
class SettingsState(
    @StringRes @Transient val title: Int = R.string.settings_title,
    override val left: ImmutableList<ScreenPart> = persistentListOf(),
    override val right: ImmutableList<ScreenPart> = persistentListOf(),
    override val full: ImmutableList<ScreenPart> = persistentListOf(),
) : DividedScreens


//listOf<SettingsScreen>(
//        SettingsList(
//            list = persistentListOf(
//                SettingsListElement(
//                    type = SettingsType.AUTH,
//                    title = R.string.settings_signin_signup,
//                ),
//                SettingsListElement(
//                    type = SettingsType.THEME,
//                    title = R.string.settings_signin_signup,
//                    subtitle = R.string.settings_theme_dark_light,
//                    switch = true,
//                    switchState = true
//                ),
//                SettingsListElement(
//                    type = SettingsType.NOTIFICATIONS,
//                    title = R.string.settings_notifications,
//                    qualityCount = "32",
//                ),
//                SettingsListElement(
//                    type = SettingsType.SUBSCRIPTION,
//                    title = R.string.settings_subscription,
//                ),
//                SettingsListElement(
//                    type = SettingsType.PRIVACY_POLICY,
//                    title = R.string.settings_privacy,
//                ),
//            )
//        )
//    ).toImmutableList(),

/**
 * List of settings in a left part of screen. Element can be selected.
 */

data class SettingsList(
    val list: ImmutableList<SettingsListElement>
) : SettingsScreen

/**
 * Element of list of settings
 */
@kotlinx.serialization.Serializable
data class SettingsListElement(
    val type: SettingsType,
    @StringRes val title: Int,
    @StringRes val subtitle: Int? = null,
    val selected: Boolean = false,
    val qualityCount: String? = null,
    val switch: Boolean = false,
    val switchState: Boolean = false,
    val disableFollow: Boolean = false
) : java.io.Serializable

/**
 * Type of a setting
 */

@kotlinx.serialization.Serializable
enum class SettingsType : java.io.Serializable {
    AUTH, THEME, NOTIFICATIONS, SUBSCRIPTION, PRIVACY_POLICY
}

