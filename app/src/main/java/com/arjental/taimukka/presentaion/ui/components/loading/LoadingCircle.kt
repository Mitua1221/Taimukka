package com.arjental.taimukka.presentaion.ui.components.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CircularProgressIndicator(
    statusBarPadding: Boolean = false,
) {

    val modifier = Modifier.fillMaxSize()
    if (statusBarPadding) modifier.statusBarsPadding()

    Box(
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier =  Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary,
        )
    }

}