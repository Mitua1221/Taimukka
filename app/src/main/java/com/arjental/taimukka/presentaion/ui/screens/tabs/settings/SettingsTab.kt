package com.arjental.taimukka.presentaion.ui.screens.tabs.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.androidx.AndroidScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.arjental.taimukka.R
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Settings

class SettingsTab() : Tab, ScreenLifecycleProvider {


    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_settings)
            val icon = rememberVectorPainter(TIcons.Settings)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() = Settings()
    override fun getLifecycleOwner(): ScreenLifecycleOwner = AndroidScreenLifecycleOwner.get(this)

}


