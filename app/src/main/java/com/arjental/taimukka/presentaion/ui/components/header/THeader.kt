package com.arjental.taimukka.presentaion.ui.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.R

/**
 * @param icon requires icon of application
 */

@Composable
fun THeader(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageBitmap? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .padding(top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Image(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                bitmap = icon,
                contentDescription = stringResource(id = R.string.applications_application_logo, title),
            )
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

    }



}