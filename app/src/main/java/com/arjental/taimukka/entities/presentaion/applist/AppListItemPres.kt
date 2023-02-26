package com.arjental.taimukka.entities.presentaion.applist

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.other.utils.annotataions.Category
import com.arjental.taimukka.other.utils.images.loadPackageIcon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@kotlinx.serialization.Serializable
data class AppListItemPres(
    val title: String,
    val packageName: String,
    val appIcon: ImageBitmap?,
    val nonSystem: Boolean,
    @Category val appCategory: Int?,
    val percentage: Float = 0f,
    val realQuality: Long = 0,
    val selectionType: SelectionType = SelectionType.SCREEN_TIME
): java.io.Serializable

suspend fun List<LaunchedAppDomain>.toPresentation(context: Context): ImmutableList<AppListItemPres> {
    return this.map {
        AppListItemPres(
            title = it.appName,
            packageName = it.appPackage,
            appIcon = context.loadPackageIcon(it.appPackage),
            nonSystem = it.nonSystem,
            appCategory = it.appCategory,
            percentage = it.percentage,
            realQuality = it.realQuality,
            selectionType = it.selectionType,
        )
    }.toImmutableList()
}