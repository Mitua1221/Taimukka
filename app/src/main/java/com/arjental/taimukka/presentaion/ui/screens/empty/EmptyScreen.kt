package com.arjental.taimukka.presentaion.ui.screens.empty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen

class EmptyScreen : Screen {

    @Composable
    override fun Content() {
        EmptyScreenContent()
    }

}

@Composable
fun EmptyScreenContent() {
    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.error).fillMaxSize()) {
        Text(textAlign = TextAlign.Center, text = "screen is empty")
    }
}