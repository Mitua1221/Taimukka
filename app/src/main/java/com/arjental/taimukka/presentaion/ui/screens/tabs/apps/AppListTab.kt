package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.arjental.taimukka.R
import com.arjental.taimukka.presentaion.ui.components.uiutils.TTab
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Applist

class AppListTab : TTab() {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_applist)
            val icon = rememberVectorPainter(TIcons.Applist)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun TContent() = AppListScreen()
}