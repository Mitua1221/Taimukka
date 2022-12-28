package com.arjental.taimukka.presentaion.ui.screens.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.window.layout.DisplayFeature
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.arjental.taimukka.R
import com.arjental.taimukka.presentaion.ui.components.app.ContentType
import com.arjental.taimukka.presentaion.ui.components.app.NavigationType
import com.arjental.taimukka.presentaion.ui.components.app.TaimukkaWrapLines
import com.arjental.taimukka.presentaion.ui.screens.app_list.AppListScreen

class MainTab(
    val contentType: ContentType,
    val navigationType: NavigationType,
    val displayFeatures: List<DisplayFeature>
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.home_tab)
            val icon = rememberVectorPainter(Icons.Filled.Home)

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


        AppListScreen(name = "HOME TAB")

        TaimukkaWrapLines(contentType = contentType, navigationType = navigationType, displayFeatures = displayFeatures,
            firstColumn = {
                LazyColumn {
                    items(100) { index ->
                        Text(text = "Item: $index")
                    }
                }
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


