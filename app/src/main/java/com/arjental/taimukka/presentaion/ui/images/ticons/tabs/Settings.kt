package com.arjental.taimukka.presentaion.ui.images.ticons.tabs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

public val TIcons.Settings: ImageVector
    @Composable
    get() {
        if (_settings != null) {
            return _settings!!
        }
        _settings = Builder(
            name = "Settings", defaultWidth = 25.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 25.0f, viewportHeight = 24.0f
        ).apply {
            val color = MaterialTheme.colorScheme.onSurfaceVariant
            path(
                fill = SolidColor(color), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(15.81f, 21.0298f)
                curveTo(15.71f, 21.7098f, 15.09f, 22.2498f, 14.35f, 22.2498f)
                horizontalLineTo(10.65f)
                curveTo(9.91f, 22.2498f, 9.29f, 21.7098f, 9.2f, 20.9798f)
                lineTo(8.93f, 19.0898f)
                curveTo(8.66f, 18.9498f, 8.4f, 18.7998f, 8.14f, 18.6298f)
                lineTo(6.34f, 19.3498f)
                curveTo(5.64f, 19.6098f, 4.87f, 19.3198f, 4.53f, 18.6998f)
                lineTo(2.7f, 15.5298f)
                curveTo(2.35f, 14.8698f, 2.5f, 14.0898f, 3.06f, 13.6498f)
                lineTo(4.59f, 12.4598f)
                curveTo(4.58f, 12.3098f, 4.57f, 12.1598f, 4.57f, 11.9998f)
                curveTo(4.57f, 11.8498f, 4.58f, 11.6898f, 4.59f, 11.5398f)
                lineTo(3.07f, 10.3498f)
                curveTo(2.48f, 9.8998f, 2.33f, 9.0898f, 2.7f, 8.4698f)
                lineTo(4.55f, 5.2798f)
                curveTo(4.89f, 4.6598f, 5.66f, 4.3798f, 6.34f, 4.6498f)
                lineTo(8.15f, 5.3798f)
                curveTo(8.41f, 5.2098f, 8.67f, 5.0598f, 8.93f, 4.9198f)
                lineTo(9.2f, 3.0098f)
                curveTo(9.29f, 2.3098f, 9.91f, 1.7598f, 10.64f, 1.7598f)
                horizontalLineTo(14.34f)
                curveTo(15.08f, 1.7598f, 15.7f, 2.2998f, 15.79f, 3.0298f)
                lineTo(16.06f, 4.9198f)
                curveTo(16.33f, 5.0598f, 16.59f, 5.2098f, 16.85f, 5.3798f)
                lineTo(18.65f, 4.6598f)
                curveTo(19.36f, 4.3998f, 20.13f, 4.6898f, 20.47f, 5.3098f)
                lineTo(22.31f, 8.4898f)
                curveTo(22.67f, 9.1498f, 22.51f, 9.9298f, 21.95f, 10.3698f)
                lineTo(20.43f, 11.5598f)
                curveTo(20.44f, 11.7098f, 20.45f, 11.8598f, 20.45f, 12.0198f)
                curveTo(20.45f, 12.1798f, 20.44f, 12.3298f, 20.43f, 12.4798f)
                lineTo(21.95f, 13.6698f)
                curveTo(22.51f, 14.1198f, 22.67f, 14.8998f, 22.32f, 15.5298f)
                lineTo(20.46f, 18.7498f)
                curveTo(20.12f, 19.3698f, 19.35f, 19.6498f, 18.66f, 19.3798f)
                lineTo(16.86f, 18.6598f)
                curveTo(16.6f, 18.8298f, 16.34f, 18.9798f, 16.08f, 19.1198f)
                lineTo(15.81f, 21.0298f)
                close()
                moveTo(11.12f, 20.2498f)
                horizontalLineTo(13.88f)
                lineTo(14.25f, 17.6998f)
                lineTo(14.78f, 17.4798f)
                curveTo(15.22f, 17.2998f, 15.66f, 17.0398f, 16.12f, 16.6998f)
                lineTo(16.57f, 16.3598f)
                lineTo(18.95f, 17.3198f)
                lineTo(20.33f, 14.9198f)
                lineTo(18.3f, 13.3398f)
                lineTo(18.37f, 12.7798f)
                lineTo(18.3731f, 12.7528f)
                curveTo(18.402f, 12.5025f, 18.43f, 12.2604f, 18.43f, 11.9998f)
                curveTo(18.43f, 11.7298f, 18.4f, 11.4698f, 18.37f, 11.2198f)
                lineTo(18.3f, 10.6598f)
                lineTo(20.33f, 9.0798f)
                lineTo(18.94f, 6.6798f)
                lineTo(16.55f, 7.6398f)
                lineTo(16.1f, 7.2898f)
                curveTo(15.68f, 6.9698f, 15.23f, 6.7098f, 14.77f, 6.5198f)
                lineTo(14.25f, 6.2998f)
                lineTo(13.88f, 3.7498f)
                horizontalLineTo(11.12f)
                lineTo(10.75f, 6.2998f)
                lineTo(10.22f, 6.5098f)
                curveTo(9.78f, 6.6998f, 9.34f, 6.9498f, 8.88f, 7.2998f)
                lineTo(8.43f, 7.6298f)
                lineTo(6.05f, 6.6798f)
                lineTo(4.66f, 9.0698f)
                lineTo(6.69f, 10.6498f)
                lineTo(6.62f, 11.2098f)
                curveTo(6.59f, 11.4698f, 6.56f, 11.7398f, 6.56f, 11.9998f)
                curveTo(6.56f, 12.2598f, 6.58f, 12.5298f, 6.62f, 12.7798f)
                lineTo(6.69f, 13.3398f)
                lineTo(4.66f, 14.9198f)
                lineTo(6.04f, 17.3198f)
                lineTo(8.43f, 16.3598f)
                lineTo(8.88f, 16.7098f)
                curveTo(9.31f, 17.0398f, 9.74f, 17.2898f, 10.21f, 17.4798f)
                lineTo(10.74f, 17.6998f)
                lineTo(11.12f, 20.2498f)
                close()
                moveTo(16.0f, 11.9998f)
                curveTo(16.0f, 13.9328f, 14.433f, 15.4998f, 12.5f, 15.4998f)
                curveTo(10.567f, 15.4998f, 9.0f, 13.9328f, 9.0f, 11.9998f)
                curveTo(9.0f, 10.0668f, 10.567f, 8.4998f, 12.5f, 8.4998f)
                curveTo(14.433f, 8.4998f, 16.0f, 10.0668f, 16.0f, 11.9998f)
                close()
            }
        }
            .build()
        return _settings!!
    }

private var _settings: ImageVector? = null
