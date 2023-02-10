package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import androidx.annotation.StringRes
import com.arjental.taimukka.R
import com.arjental.taimukka.entities.presentaion.applist.AppListItemPres
import com.arjental.taimukka.presentaion.ui.components.uiutils.AdditionalError
import com.arjental.taimukka.presentaion.ui.components.uiutils.DividedScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.AdditionalScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts.ApplicationsListPart
import com.arjental.taimukka.presentaion.ui.screens.tabs.settings.SettingsListElement
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Transient


@kotlinx.serialization.Serializable
class AppListState(
    override val left: ImmutableList<ScreenPart> = persistentListOf(
        ApplicationsListPart(),
    ),
    override val right: ImmutableList<ScreenPart> = persistentListOf(

    ),
    override val full: ImmutableList<ScreenPart> = persistentListOf(),
) : DividedScreens

@kotlinx.serialization.Serializable
data class ApplicationsListState(
    @StringRes @Transient val title: Int = R.string.applications_title,
    val list: ImmutableList<AppListItemPres> = persistentListOf(),
    override val loading: Boolean = true,
    override val error: AdditionalError? = null,
) : AdditionalScreens(), java.io.Serializable
