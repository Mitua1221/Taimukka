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

public val TIcons.Seances: ImageVector
    get() {
        if (_seances != null) {
            return _seances!!
        }
        _seances = Builder(
            name = "Seances", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF121F0F)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(4.0f, 21.0f)
                verticalLineTo(3.0f)
                curveTo(4.0f, 1.9f, 4.9f, 1.01f, 6.0f, 1.01f)
                lineTo(16.0f, 1.0f)
                curveTo(17.1f, 1.0f, 18.0f, 1.9f, 18.0f, 3.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(18.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(17.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(21.0f)
                curveTo(18.0f, 22.1f, 17.1f, 23.0f, 16.0f, 23.0f)
                horizontalLineTo(6.0f)
                curveTo(4.9f, 23.0f, 4.0f, 22.1f, 4.0f, 21.0f)
                close()
                moveTo(16.0f, 21.0f)
                verticalLineTo(20.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(16.0f)
                close()
                moveTo(6.0f, 3.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(3.0f)
                close()
                moveTo(21.2f, 9.55f)
                lineTo(19.8f, 8.15f)
                lineTo(14.75f, 13.2f)
                lineTo(12.4f, 10.85f)
                lineTo(11.0f, 12.25f)
                lineTo(14.75f, 16.0f)
                lineTo(21.2f, 9.55f)
                close()
            }
        }
            .build()
        return _seances!!
    }

private var _seances: ImageVector? = null
