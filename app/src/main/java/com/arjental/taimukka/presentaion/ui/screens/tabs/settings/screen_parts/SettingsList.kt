package com.arjental.taimukka.presentaion.ui.screens.tabs.settings.screen_parts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart

class SettingsList: ScreenPart {

    @Composable
    override fun Content() {
        SettingsListContent()
    }


}

@Composable
fun SettingsListContent() {
    LazyColumn {

        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
                .padding(top = 16.dp, bottom = 24.dp)) {
                Text(
                    text = stringResource(id = appListVM.title),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        appListVM.left.forEach { screen ->
            item {
                Row(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = screen,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(id = appListVM.title),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }





            }
        }

    }
}