package com.arjental.taimukka.presentaion.ui.screens.tabs.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.app.TWrapLines
import com.arjental.taimukka.presentaion.ui.components.uiutils.TPreviewWrap

@Preview
@Composable
fun foo() {
    TPreviewWrap() {
        Settings()
    }
}

@Composable
fun Settings() {

    val settingsVM = daggerViewModel<SettingsVM>()
    val state = settingsVM.collectState().collectAsState()

    TWrapLines(
        firstColumnScreens = state.value.left,
        secondColumnScreens = state.value.right
    )

}