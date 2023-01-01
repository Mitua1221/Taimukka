package com.arjental.taimukka.entities.presentaion.applist

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.other.utils.images.loadPackageIcon

data class AppListItemPres(
    val title: String,
    val packageName: String,
    val appIcon: ImageBitmap?,
    val nonSystem: Boolean,
)

suspend fun List<LaunchedAppDomain>.toPresentation(context: Context): List<AppListItemPres> {
    return this.map {
        AppListItemPres(
            title = it.appName,
            packageName = it.appPackage,
            appIcon = context.loadPackageIcon(it.appPackage),
            nonSystem = it.nonSystem
        )
    }
}