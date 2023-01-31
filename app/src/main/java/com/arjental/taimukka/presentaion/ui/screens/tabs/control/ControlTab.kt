package com.arjental.taimukka.presentaion.ui.screens.tabs.control

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.arjental.taimukka.R
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Control
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Stats

class ControlTab() : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_control)
            val icon = rememberVectorPainter(TIcons.Control)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
    }


}


