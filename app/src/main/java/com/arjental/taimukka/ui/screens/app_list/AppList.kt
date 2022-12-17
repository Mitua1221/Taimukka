package com.arjental.taimukka.ui.screens.app_list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.App
import com.arjental.taimukka.ui.theme.TaimukkaTheme
import com.arjental.taimukka.utils.factories.viewmodel.Inject
import com.arjental.taimukka.utils.factories.viewmodel.daggerViewModel

@Composable
fun AppListScreen(
    name: String,
) {
    Text(text = "Hello asad $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaimukkaTheme {
        //MainScreen("Android")
    }
}