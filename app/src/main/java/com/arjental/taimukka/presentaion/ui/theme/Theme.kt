package com.arjental.taimukka.presentaion.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.ViewCompat
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalDisplayFeatures

@Composable
private fun produceColorScheme(): ColorScheme {
    return ColorScheme(
        primary = colorResource(id = com.arjental.taimukka.R.color.primary),
        onPrimary = colorResource(id = com.arjental.taimukka.R.color.onPrimary),
        primaryContainer = colorResource(id = com.arjental.taimukka.R.color.primaryContainer),
        onPrimaryContainer = colorResource(id = com.arjental.taimukka.R.color.onPrimaryContainer),
        inversePrimary = colorResource(id = com.arjental.taimukka.R.color.inversePrimary),
        secondary = colorResource(id = com.arjental.taimukka.R.color.secondary),
        onSecondary = colorResource(id = com.arjental.taimukka.R.color.onSecondary),
        secondaryContainer = colorResource(id = com.arjental.taimukka.R.color.secondaryContainer),
        onSecondaryContainer = colorResource(id = com.arjental.taimukka.R.color.onSecondaryContainer),
        tertiary = colorResource(id = com.arjental.taimukka.R.color.tertiary),
        onTertiary = colorResource(id = com.arjental.taimukka.R.color.onTertiary),
        tertiaryContainer = colorResource(id = com.arjental.taimukka.R.color.tertiaryContainer),
        onTertiaryContainer = colorResource(id = com.arjental.taimukka.R.color.onTertiaryContainer),
        background = colorResource(id = com.arjental.taimukka.R.color.background),
        onBackground = colorResource(id = com.arjental.taimukka.R.color.onBackground),
        surface = colorResource(id = com.arjental.taimukka.R.color.surface),
        onSurface = colorResource(id = com.arjental.taimukka.R.color.onSurface),
        surfaceVariant = colorResource(id = com.arjental.taimukka.R.color.surfaceVariant),
        onSurfaceVariant = colorResource(id = com.arjental.taimukka.R.color.onSurfaceVariant),
        surfaceTint = colorResource(id = com.arjental.taimukka.R.color.surfaceTint),
        inverseSurface = colorResource(id = com.arjental.taimukka.R.color.inverseSurface),
        inverseOnSurface = colorResource(id = com.arjental.taimukka.R.color.inverseOnSurface),
        error = colorResource(id = com.arjental.taimukka.R.color.error),
        onError = colorResource(id = com.arjental.taimukka.R.color.onError),
        errorContainer = colorResource(id = com.arjental.taimukka.R.color.errorContainer),
        onErrorContainer = colorResource(id = com.arjental.taimukka.R.color.onErrorContainer),
        outline = colorResource(id = com.arjental.taimukka.R.color.outline),
        outlineVariant = colorResource(id = com.arjental.taimukka.R.color.outlineVariant),
        scrim = colorResource(id = com.arjental.taimukka.R.color.scrim),
    )
}

@Composable
fun TaimukkaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> produceColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            // (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        shapes = shapes,
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}