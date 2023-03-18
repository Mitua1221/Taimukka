package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import com.arjental.taimukka.R
import com.arjental.taimukka.entities.presentaion.app_details.AppDetailedPresentation
import com.arjental.taimukka.entities.presentaion.applist.AppListItemPres
import com.arjental.taimukka.presentaion.ui.components.loading.LoadingPart
import com.arjental.taimukka.presentaion.ui.components.uiutils.AdditionalError
import com.arjental.taimukka.presentaion.ui.components.uiutils.AdditionalScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.DividedScreens
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts.ApplicationDetailsPart
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts.ApplicationsListPart
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Transient


@kotlinx.serialization.Serializable
data class AppListState(
    override val left: ImmutableList<ScreenPart> = persistentListOf(
        ApplicationsListPart(),
    ),
    override val right: ImmutableList<ScreenPart> = persistentListOf(
        LoadingPart()
    ),
    override val full: ImmutableList<ScreenPart> = persistentListOf(),
) : DividedScreens {

    override fun elevated(screenPart: ScreenPart, foundInRight: Boolean): DividedScreens {
        return this.copy(right = right.inverseElevated(screenPart))
    }

}

@Immutable
@kotlinx.serialization.Serializable
data class ApplicationsListState(
    @StringRes @Transient val title: Int = R.string.applications_title,
    val list: ImmutableList<AppListItemPres> = persistentListOf(),
    override val loading: Boolean = true,
    override val error: AdditionalError? = null,
) : AdditionalScreens(), java.io.Serializable

@Immutable
@kotlinx.serialization.Serializable
data class ApplicationDetailsState(
    val appPresentationInformation: AppDetailedPresentation? = null,
    override val loading: Boolean = true,
    override val error: AdditionalError? = null,
) : AdditionalScreens(), java.io.Serializable

