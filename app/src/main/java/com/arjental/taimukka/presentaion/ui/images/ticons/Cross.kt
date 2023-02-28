package com.arjental.taimukka.presentaion.ui.images.ticons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.presentaion.ui.images.TIcons

public val TIcons.Cross: ImageVector
    @Composable
    get() {
        val color = MaterialTheme.colorScheme.outline
        if (_cross != null) {
            return _cross!!
        }
        _cross = Builder(
            name = "Cross", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth =
            24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(color), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.0f, 6.41f)
                lineTo(17.59f, 5.0f)
                lineTo(12.0f, 10.59f)
                lineTo(6.41f, 5.0f)
                lineTo(5.0f, 6.41f)
                lineTo(10.59f, 12.0f)
                lineTo(5.0f, 17.59f)
                lineTo(6.41f, 19.0f)
                lineTo(12.0f, 13.41f)
                lineTo(17.59f, 19.0f)
                lineTo(19.0f, 17.59f)
                lineTo(13.41f, 12.0f)
                lineTo(19.0f, 6.41f)
                close()
            }
        }
            .build()
        return _cross!!
    }

private var _cross: ImageVector? = null
