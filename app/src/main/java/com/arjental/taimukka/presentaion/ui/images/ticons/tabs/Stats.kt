package com.arjental.taimukka.presentaion.ui.images.ticons.tabs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

public val TIcons.Stats: ImageVector
    @Composable
    get() {
        if (_stats != null) {
            return _stats!!
        }
        _stats = Builder(
            name = "Stats", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            val color = MaterialTheme.colorScheme.onSurfaceVariant
            group {
                path(
                    fill = SolidColor(color), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(12.0f, 21.9998f)
                    curveTo(10.6167f, 21.9998f, 9.3167f, 21.7371f, 8.1f, 21.2118f)
                    curveTo(6.8833f, 20.6871f, 5.825f, 19.9748f, 4.925f, 19.0748f)
                    curveTo(4.025f, 18.1748f, 3.3127f, 17.1165f, 2.788f, 15.8998f)
                    curveTo(2.2627f, 14.6831f, 2.0f, 13.3831f, 2.0f, 11.9998f)
                    curveTo(2.0f, 9.4165f, 2.8583f, 7.1748f, 4.575f, 5.2748f)
                    curveTo(6.2917f, 3.3748f, 8.4333f, 2.2998f, 11.0f, 2.0498f)
                    verticalLineTo(5.0498f)
                    curveTo(9.2667f, 5.2831f, 7.8333f, 6.0581f, 6.7f, 7.3748f)
                    curveTo(5.5667f, 8.6915f, 5.0f, 10.2331f, 5.0f, 11.9998f)
                    curveTo(5.0f, 13.9498f, 5.6793f, 15.6038f, 7.038f, 16.9618f)
                    curveTo(8.396f, 18.3205f, 10.05f, 18.9998f, 12.0f, 18.9998f)
                    curveTo(13.0667f, 18.9998f, 14.0793f, 18.7748f, 15.038f, 18.3248f)
                    curveTo(15.996f, 17.8748f, 16.8167f, 17.2331f, 17.5f, 16.3998f)
                    lineTo(20.1f, 17.8998f)
                    curveTo(19.15f, 19.1998f, 17.9667f, 20.2081f, 16.55f, 20.9248f)
                    curveTo(15.1333f, 21.6415f, 13.6167f, 21.9998f, 12.0f, 21.9998f)
                    close()
                    moveTo(21.15f, 16.0498f)
                    lineTo(18.55f, 14.5498f)
                    curveTo(18.7f, 14.1331f, 18.8127f, 13.7121f, 18.888f, 13.2868f)
                    curveTo(18.9627f, 12.8621f, 19.0f, 12.4331f, 19.0f, 11.9998f)
                    curveTo(19.0f, 10.2331f, 18.4333f, 8.6915f, 17.3f, 7.3748f)
                    curveTo(16.1667f, 6.0581f, 14.7333f, 5.2831f, 13.0f, 5.0498f)
                    verticalLineTo(2.0498f)
                    curveTo(15.5667f, 2.2998f, 17.7083f, 3.3748f, 19.425f, 5.2748f)
                    curveTo(21.1417f, 7.1748f, 22.0f, 9.4165f, 22.0f, 11.9998f)
                    curveTo(22.0f, 12.6998f, 21.9373f, 13.3915f, 21.812f, 14.0748f)
                    curveTo(21.6873f, 14.7581f, 21.4667f, 15.4165f, 21.15f, 16.0498f)
                    close()
                }
            }
        }
            .build()
        return _stats!!
    }

private var _stats: ImageVector? = null
