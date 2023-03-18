package com.arjental.taimukka.presentaion.ui.images.ticons.catagories

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.presentaion.ui.images.TIcons

public val TIcons.Timeline: ImageVector
    get() {
        if (_timeline != null) {
            return _timeline!!
        }
        _timeline = Builder(
            name = "Timeline", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF121F0F)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(11.99f, 2.0f)
                curveTo(6.47f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                curveTo(2.0f, 17.52f, 6.47f, 22.0f, 11.99f, 22.0f)
                curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f)
                curveTo(22.0f, 6.48f, 17.52f, 2.0f, 11.99f, 2.0f)
                close()
                moveTo(12.0f, 20.0f)
                curveTo(7.58f, 20.0f, 4.0f, 16.42f, 4.0f, 12.0f)
                curveTo(4.0f, 7.58f, 7.58f, 4.0f, 12.0f, 4.0f)
                curveTo(16.42f, 4.0f, 20.0f, 7.58f, 20.0f, 12.0f)
                curveTo(20.0f, 16.42f, 16.42f, 20.0f, 12.0f, 20.0f)
                close()
                moveTo(11.0f, 13.07f)
                lineTo(16.49f, 16.36f)
                lineTo(17.51f, 14.64f)
                lineTo(13.0f, 11.93f)
                verticalLineTo(6.42f)
                horizontalLineTo(11.0f)
                verticalLineTo(13.07f)
                close()
            }
        }
            .build()
        return _timeline!!
    }

private var _timeline: ImageVector? = null
