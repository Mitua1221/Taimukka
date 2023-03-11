package com.arjental.taimukka.presentaion.ui.images.ticons.catagories

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.presentaion.ui.images.TIcons

public val TIcons.Notifications: ImageVector
    get() {
        if (_notifications != null) {
            return _notifications!!
        }
        _notifications = Builder(
            name = "Notifications", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF121F0F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(2.0f, 10.0f)
                    curveTo(2.0f, 8.5333f, 2.296f, 7.146f, 2.888f, 5.838f)
                    curveTo(3.4793f, 4.5293f, 4.325f, 3.4f, 5.425f, 2.45f)
                    lineTo(6.85f, 3.85f)
                    curveTo(5.95f, 4.6333f, 5.25f, 5.554f, 4.75f, 6.612f)
                    curveTo(4.25f, 7.6707f, 4.0f, 8.8f, 4.0f, 10.0f)
                    horizontalLineTo(2.0f)
                    close()
                    moveTo(20.0f, 10.0f)
                    curveTo(20.0f, 8.8f, 19.75f, 7.6707f, 19.25f, 6.612f)
                    curveTo(18.75f, 5.554f, 18.05f, 4.6333f, 17.15f, 3.85f)
                    lineTo(18.575f, 2.45f)
                    curveTo(19.675f, 3.4f, 20.521f, 4.5293f, 21.113f, 5.838f)
                    curveTo(21.7043f, 7.146f, 22.0f, 8.5333f, 22.0f, 10.0f)
                    horizontalLineTo(20.0f)
                    close()
                    moveTo(4.0f, 19.0f)
                    verticalLineTo(17.0f)
                    horizontalLineTo(6.0f)
                    verticalLineTo(10.0f)
                    curveTo(6.0f, 8.6167f, 6.4167f, 7.3873f, 7.25f, 6.312f)
                    curveTo(8.0833f, 5.2373f, 9.1667f, 4.5333f, 10.5f, 4.2f)
                    verticalLineTo(3.5f)
                    curveTo(10.5f, 3.0833f, 10.646f, 2.7293f, 10.938f, 2.438f)
                    curveTo(11.2293f, 2.146f, 11.5833f, 2.0f, 12.0f, 2.0f)
                    curveTo(12.4167f, 2.0f, 12.7707f, 2.146f, 13.062f, 2.438f)
                    curveTo(13.354f, 2.7293f, 13.5f, 3.0833f, 13.5f, 3.5f)
                    verticalLineTo(4.2f)
                    curveTo(14.8333f, 4.5333f, 15.9167f, 5.2373f, 16.75f, 6.312f)
                    curveTo(17.5833f, 7.3873f, 18.0f, 8.6167f, 18.0f, 10.0f)
                    verticalLineTo(17.0f)
                    horizontalLineTo(20.0f)
                    verticalLineTo(19.0f)
                    horizontalLineTo(4.0f)
                    close()
                    moveTo(12.0f, 22.0f)
                    curveTo(11.45f, 22.0f, 10.9793f, 21.8043f, 10.588f, 21.413f)
                    curveTo(10.196f, 21.021f, 10.0f, 20.55f, 10.0f, 20.0f)
                    horizontalLineTo(14.0f)
                    curveTo(14.0f, 20.55f, 13.8043f, 21.021f, 13.413f, 21.413f)
                    curveTo(13.021f, 21.8043f, 12.55f, 22.0f, 12.0f, 22.0f)
                    close()
                    moveTo(8.0f, 17.0f)
                    horizontalLineTo(16.0f)
                    verticalLineTo(10.0f)
                    curveTo(16.0f, 8.9f, 15.6083f, 7.9583f, 14.825f, 7.175f)
                    curveTo(14.0417f, 6.3917f, 13.1f, 6.0f, 12.0f, 6.0f)
                    curveTo(10.9f, 6.0f, 9.9583f, 6.3917f, 9.175f, 7.175f)
                    curveTo(8.3917f, 7.9583f, 8.0f, 8.9f, 8.0f, 10.0f)
                    verticalLineTo(17.0f)
                    close()
                }
            }
        }
            .build()
        return _notifications!!
    }

private var _notifications: ImageVector? = null
