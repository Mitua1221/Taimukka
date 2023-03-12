package com.arjental.taimukka.entities.presentaion.app_details

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDetailedDomain
import com.arjental.taimukka.entities.pierce.selection_type.Type
import com.arjental.taimukka.other.utils.annotataions.Category
import com.arjental.taimukka.other.utils.images.loadPackageIcon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Object of presentation app detailed
 * @param detailedList horizontal list
 * @param detailedAdditionalList vertical additional list
 */

@Immutable
@kotlinx.serialization.Serializable
data class AppDetailedPresentation(
    val appName: String = "",
    val appPackage: String = "",
    val appIcon: ImageBitmap? = null,
    @Category val appCategory: Int?,
    val detailedList: ImmutableList<AppDetailedListItemPresentation>,
    val detailedAdditionalList: ImmutableList<AppDetailedListItemPresentation>,
) : java.io.Serializable

@Immutable
@kotlinx.serialization.Serializable
data class AppDetailedListItemPresentation(
    val type: Type,
    val percentage: Float = 0f,
    val value: Long,
) : java.io.Serializable

suspend fun LaunchedAppDetailedDomain.toPresentation(
    context: Context
): AppDetailedPresentation {

    return AppDetailedPresentation(
        appName = appName,
        appPackage = appPackage,
        appIcon = context.loadPackageIcon(this.appPackage),
        appCategory = appCategory,
        detailedList = persistentListOf(
            AppDetailedListItemPresentation( //screen time
                type = Type.SCREEN_TIME,
                percentage = screenTimePercentage,
                value = screenTimeMillis
            ),
            AppDetailedListItemPresentation( //screen time
                type = Type.NOTIFICATIONS_RECEIVED,
                percentage = notificationsPercentage,
                value = notificationsQuality.toLong()
            ),
            AppDetailedListItemPresentation(
                type = Type.SEANCES,
                percentage = seancesPercentage,
                value = seancesQuality.toLong()
            )
        ),
        detailedAdditionalList = persistentListOf(
            AppDetailedListItemPresentation( //screen time
                type = Type.NOTIFICATIONS_SEEN,
                percentage = 0f,
                value = notificationsSeenQuality.toLong()
            ),
        )
    )

}

