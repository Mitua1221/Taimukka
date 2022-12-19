package com.arjental.taimukka.presentaion.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.App
import com.arjental.taimukka.presentaion.ui.theme.TaimukkaTheme
import com.arjental.taimukka.other.utils.factories.viewmodel.Inject
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun MainScreen(
    name: String,
    viewModel: MainViewModel = daggerViewModel(),
) {

    val f = MutableStateFlow(10).asStateFlow()
    viewModel.launch()
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaimukkaTheme {
        MainScreen("Android")
    }
}