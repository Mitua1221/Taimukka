package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.app.TWrapLines
import com.arjental.taimukka.presentaion.ui.components.navigations.wNav
import com.arjental.taimukka.presentaion.ui.components.uiutils.TPreviewWrap

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TPreviewWrap() {
        AppListScreen()
    }
}

@Composable
fun AppListScreen() {

    val appListVM = daggerViewModel<AppListVM>()
    val state = appListVM.collectState().collectAsState()

    TWrapLines(
        firstColumnScreens = state.value.left,
        secondColumnScreens = state.value.right,
        singleRawBackPressed = {
            wNav(vm = appListVM, it)
        }
    )

}

