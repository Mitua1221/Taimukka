package com.arjental.taimukka.presentaion.ui.screens.tabs.settings.screen_parts

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.header.THeader
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
            THeader(
                modifier = Modifier.padding(top = 8.dp),
                title = stringResource(id = state.value.title)
            )
        }

        state.value.list.forEach { screen ->
            item(
                key = screen.type.name
            ) {
                val context = LocalContext.current
                Box(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .then(
                            when (screen.type) {
                                SettingsType.THEME -> Modifier
                                else -> Modifier.clickable {
                                    when {
                                        !screen.clickableUrl.isNullOrEmpty() -> {
                                            context.startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(screen.clickableUrl)
                                                )
                                            )
                                        }
                                        else -> settingsVM.clickOnSettingsItem(screen.type)
                                    }
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
                        val icon = rememberVectorPainter(TIcons.Follow)
                        Image(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp), painter = icon, contentDescription = TIcons.Follow.name,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
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


    }
}