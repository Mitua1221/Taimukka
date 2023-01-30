package com.arjental.taimukka.presentaion.ui.components.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Face
import com.arjental.taimukka.presentaion.ui.theme.toOnBoardingSign

@Composable
fun TBoxBackground(
    modifier: Modifier = Modifier,
    isFaceOnBackground: Boolean = false,
    bottomText: Boolean = false,
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
        if (bottomText) {
            Text(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 14.dp),
                text = stringResource(id = com.arjental.taimukka.R.string.app_name).uppercase(),
                style = MaterialTheme.typography.headlineLarge.toOnBoardingSign(),
                color = MaterialTheme.colorScheme.primary
            )
        }
        content()
    }
}