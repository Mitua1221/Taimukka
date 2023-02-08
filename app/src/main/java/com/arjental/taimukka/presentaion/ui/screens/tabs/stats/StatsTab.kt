package com.arjental.taimukka.presentaion.ui.screens.tabs.stats

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.arjental.taimukka.R
import com.arjental.taimukka.presentaion.ui.components.uiutils.TTab
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Stats

class StatsTab() : TTab() {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_home)
            val icon = rememberVectorPainter(TIcons.Stats)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun TContent() {

//        val mainViewModel = daggerViewModel<MainViewModel>()
//        mainViewModel.loadApplicationStats()
//
//        TaimukkaWrapLines(
//            firstColumn = {
//                val applicationsListState = mainViewModel.collectState().collectAsState().value
//                when (applicationsListState) {
//                    is MainState.PageLoaded -> {
//                        LazyColumn {
//                            items(applicationsListState.list.size) { index ->
//                                Text(text = "Item: ${applicationsListState.list[index]}")
//                            }
//                        }
//                    }
//                    else -> {}
//                }
//
//            },
//            secondColumn = {
//                LazyColumn {
//                    items(100) { index ->
//                        Text(text = "Item: $index")
//                    }
//                }
//            }
//
//        )

    }


}


