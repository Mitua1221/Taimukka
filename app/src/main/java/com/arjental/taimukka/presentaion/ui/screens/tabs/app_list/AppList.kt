package com.arjental.taimukka.presentaion.ui.screens.tabs.app_list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.presentaion.ui.theme.TaimukkaTheme

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