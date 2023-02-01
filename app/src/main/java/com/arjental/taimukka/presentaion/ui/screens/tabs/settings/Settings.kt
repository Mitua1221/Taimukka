package com.arjental.taimukka.presentaion.ui.screens.tabs.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.app.TWrapLines
import com.arjental.taimukka.presentaion.ui.components.uiutils.PreviewWrap

@Preview
@Composable
fun foo() {
    PreviewWrap() {
        SettingsList()
    }
}

@Composable
fun Settings() {

   // val appListVM = daggerViewModel<SettingsVM>()

    val state = SettingsState()

    TWrapLines(
        firstColumn = {

        },
        secondColumn = {
            LazyColumn {
                items(100) { index ->
                    Text(text = "Item: $index")
                }
            }
        }

    )



   // SettingsList()

}

@Composable
fun SettingsList() {





}