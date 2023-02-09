package com.arjental.taimukka.presentaion.ui.screens.tabs.stats

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.presentaion.ui.theme.TaimukkaTheme
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel

@Composable
fun MainScreen(
    name: String,
    viewModel: StatsVM = daggerViewModel(),
) {
    //viewModel.collect()


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaimukkaTheme {
        MainScreen("Android")
    }
}