package com.arjental.taimukka.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arjental.taimukka.ui.theme.TaimukkaTheme

@Composable
fun MainScreen(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaimukkaTheme {
        MainScreen("Android")
    }
}