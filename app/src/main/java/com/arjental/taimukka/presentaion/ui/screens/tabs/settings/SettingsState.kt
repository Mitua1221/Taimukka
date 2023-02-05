package com.arjental.taimukka.presentaion.ui.screens.tabs.settings

import androidx.annotation.StringRes
import com.arjental.taimukka.R
import com.arjental.taimukka.presentaion.ui.components.uiutils.DividedScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.LoadingScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.screens.tabs.settings.screen_parts.AuthorizationPart
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Transient


/**
 * All list element state - left, right, whole screen
 */
@kotlinx.serialization.Serializable
class SettingsState(
    override val left: ImmutableList<ScreenPart> = persistentListOf(
        com.arjental.taimukka.presentaion.ui.screens.tabs.settings.screen_parts.SettingsList(),
    ),
    override val right: ImmutableList<ScreenPart> = persistentListOf(
        AuthorizationPart()
    ),
    override val full: ImmutableList<ScreenPart> = persistentListOf(),
) : DividedScreens


/**
 * List of settings in a left part of screen. Element can be selected.
 */

@kotlinx.serialization.Serializable
data class SettingsListElements(
    @StringRes @Transient val title: Int = R.string.settings_title,
    val list: ImmutableList<SettingsListElement> = persistentListOf()
) : LoadingScreens(), java.io.Serializable

suspend fun SettingsListElements.createList(
    useDefaultThemeSwitchState: Boolean,
    forceDarkModeSwitchState: Boolean,
    forceDarkModeSwitchEnabled: Boolean
): SettingsListElements {
    return this.copy(
        list = persistentListOf(
            SettingsListElement(
                type = SettingsType.AUTH,
                title = R.string.settings_signin_signup,
            ),
            SettingsListElement(
                type = SettingsType.THEME_DEFAULT,
                title = R.string.settings_theme_system,
                subtitle = R.string.settings_theme_system_disc,
                switch = true,
                switchState = useDefaultThemeSwitchState
            ),
            SettingsListElement(
                type = SettingsType.THEME,
                title = R.string.settings_theme,
                subtitle = R.string.settings_theme_desc,
                switch = true,
                switchEnabled = forceDarkModeSwitchEnabled,
                switchState = forceDarkModeSwitchState,
            ),
            SettingsListElement(
                type = SettingsType.NOTIFICATIONS,
                title = R.string.settings_notifications,
                qualityCount = "32",
            ),
            SettingsListElement(
                type = SettingsType.SUBSCRIPTION,
                title = R.string.settings_subscription,
            ),
            SettingsListElement(
                type = SettingsType.PRIVACY_POLICY,
                title = R.string.settings_privacy,
            ),
        )
    )
}

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
    val switchEnabled: Boolean = true,
    val disableFollow: Boolean = false
) : java.io.Serializable

/**
 * Type of a setting
 */

@kotlinx.serialization.Serializable
enum class SettingsType : java.io.Serializable {
    AUTH, THEME_DEFAULT, THEME, NOTIFICATIONS, SUBSCRIPTION, PRIVACY_POLICY
}

