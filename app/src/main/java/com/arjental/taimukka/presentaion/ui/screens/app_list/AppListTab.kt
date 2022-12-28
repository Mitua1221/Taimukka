package com.arjental.taimukka.presentaion.ui.screens.app_list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.arjental.taimukka.R

class AppListTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.app_list_tab)
            val icon = rememberVectorPainter(Icons.Filled.List)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        AppListScreen(name = "app_list_tab")
    }
}