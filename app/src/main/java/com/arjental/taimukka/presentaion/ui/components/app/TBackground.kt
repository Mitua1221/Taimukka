package com.arjental.taimukka.presentaion.ui.components.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Face

@Composable
fun TBoxBackground(
    modifier: Modifier = Modifier,
    isFaceOnBackground: Boolean = false,

    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        if (isFaceOnBackground) {
            val image = TIcons.Face
            Image(
                modifier = Modifier.align(Alignment.BottomEnd),
                imageVector = image, contentDescription = image.name)
        }
        content()
    }
}