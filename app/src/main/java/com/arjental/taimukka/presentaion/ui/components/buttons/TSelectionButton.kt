package com.arjental.taimukka.presentaion.ui.components.buttons

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.presentaion.ui.components.uiutils.tSpring
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Follow

@Preview
@Composable
fun foo() {
    TSelectionButton(modifier = Modifier, onClick = {}, text = "text", icon = TIcons.Follow)
}

@Composable
fun TSelectionButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector?,
    iconRotated: Boolean = false,
    onClick: () -> Unit,
) {
    val angle: Float by animateFloatAsState(targetValue = if (iconRotated) 90f else 0f, animationSpec = tSpring())
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        colors = selectionButtonColors(),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        contentPadding = selectionButtonContentPadding()
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        icon?.let { image ->
            Image(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(24.dp)
                    .width(24.dp)
                    .rotate(angle), imageVector = image, contentDescription = image.name
            )
        }
    }
}

@Composable
fun selectionButtonColors() =
    buttonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

@Composable
fun selectionButtonContentPadding() =
    PaddingValues(
        start = 24.dp,
        top = 14.dp,
        end = 24.dp,
        bottom = 14.dp,
    )


