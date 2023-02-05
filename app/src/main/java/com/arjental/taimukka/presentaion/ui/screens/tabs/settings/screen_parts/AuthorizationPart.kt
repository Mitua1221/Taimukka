package com.arjental.taimukka.presentaion.ui.screens.tabs.settings.screen_parts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart

class AuthorizationPart : ScreenPart {

    @Composable
    override fun Content() =
        AuthorizationContent()


}

@Composable
fun AuthorizationContent() {

    LazyColumn {
        (0..100).forEach {
            item {
                Text(text = it.toString())
            }
        }
    }


}