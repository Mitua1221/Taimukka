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

public val TIcons.Follow: ImageVector
    @Composable
    get() {
        if (_follow != null) {
            return _follow!!
        }
        _follow = Builder(
            name = "Follow", defaultWidth = 24.0.dp, defaultHeight = 25.0.dp, viewportWidth =
            24.0f, viewportHeight = 25.0f
        ).apply {
            val color = MaterialTheme.colorScheme.onSurface
            path(
                fill = SolidColor(color), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.0f, 17.5f)
                lineTo(15.0f, 12.5f)
                lineTo(10.0f, 7.5f)
                verticalLineTo(17.5f)
                close()
            }
        }
            .build()
        return _follow!!
    }

private var _follow: ImageVector? = null
