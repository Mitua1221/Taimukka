package com.arjental.taimukka.ui.screens.app_list

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

object AppListTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "App list TAb"//stringResource(R.string.home_tab)
            val icon = rememberVectorPainter(Icons.Default.List)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        AppListScreen(name = "naiasdasdnnnn")
    }
}