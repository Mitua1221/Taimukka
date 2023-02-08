package com.arjental.taimukka.presentaion.ui.screens.tabs.settings.screen_parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Follow
import com.arjental.taimukka.presentaion.ui.screens.tabs.settings.SettingsType
import com.arjental.taimukka.presentaion.ui.screens.tabs.settings.SettingsVM

class SettingsList : ScreenPart() {

    override val implementParentLifecycle: Boolean = true

    @Composable
    override fun TContent() = SettingsListContent()

}

@Composable
fun SettingsListContent() {

    val settingsVM = daggerViewModel<SettingsVM>()
    val state = settingsVM.settingsListState().collectAsState()

    LazyColumn {

        item(
            key = "settingsTitle"
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
                    .padding(top = 24.dp, bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(id = state.value.title),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        state.value.list.forEach { screen ->
            item(
                key = screen.type.name
            ) {
                Box(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .then(
                            when (screen.type) {
                                SettingsType.THEME -> Modifier
                                else -> Modifier.clickable {
                                    settingsVM.clickOnSettingsItem(screen.type)
                                }
                            }
                        )
                ) {
                    Row {
                        Column {
                            val title = stringResource(id = screen.title)
                            val subtitle = screen.subtitle?.let { stringResource(id = screen.subtitle) }

                            Text(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .then(
                                        if (subtitle == null) Modifier.padding(vertical = 16.dp)
                                        else Modifier.padding(top = 8.dp)
                                    ),
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            if (subtitle != null) Text(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .padding(bottom = 8.dp),
                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    if (screen.switch) {
                        Switch(modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp), checked = screen.switchState,
                            enabled = screen.switchEnabled,
                            onCheckedChange = {
                                settingsVM.onSwitchChanged(type = screen.type, state = it)
                        })
                    } else {
                        Image(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp), imageVector = TIcons.Follow, contentDescription = TIcons.Follow.name
                        )
                    }


                    val color = MaterialTheme.colorScheme.outlineVariant
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .align(Alignment.BottomCenter)
                            .background(color)
                    )
                }
            }
        }

        item {
            Navigator(screen = AuthorizationPart()) {
                CurrentScreen()
            }

        }

    }
}