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
    val selectionType: SelectionType
): java.io.Serializable

suspend fun List<LaunchedAppDomain>.toPresentation(context: Context): ImmutableList<AppListItemPres> {
    return this.map {
        it.toPresentation(context)
    }.toImmutableList()
}

suspend fun LaunchedAppDomain.toPresentation(context: Context) =
    AppListItemPres(
        title = this.appName,
        packageName = this.appPackage,
        appIcon = context.loadPackageIcon(this.appPackage),
        nonSystem = this.nonSystem,
        appCategory = this.appCategory,
        percentage = this.percentage,
        realQuality = this.realQuality,
        selectionType = this.selectionType,
    )


/**
 * Filter list to show only values with category
 */

suspend fun ImmutableList<AppListItemPres>.filterWithCategory(categoryType: Int?): ImmutableList<AppListItemPres> {
    if (categoryType == null) return this
    return this.filter { it.appCategory == categoryType }.toImmutableList()
}