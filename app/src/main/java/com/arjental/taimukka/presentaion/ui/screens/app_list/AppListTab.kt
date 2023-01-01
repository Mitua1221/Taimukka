package com.arjental.taimukka.presentaion.ui.screens.app_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.arjental.taimukka.R
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.app.TaimukkaWrapLines
import com.arjental.taimukka.presentaion.ui.components.list.AppList

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

        val appListVM = daggerViewModel<AppListVM>()
        appListVM.loadApplicationStats()

        TaimukkaWrapLines(
            firstColumn = {
                val applicationsListState = appListVM.collectState().collectAsState().value
                AppList(applicationsListState)
            },
            secondColumn = {
                LazyColumn {
                    items(100) { index ->
                        Text(text = "Item: $index")
                    }
                }
            }

        )

    }
}