package com.arjental.taimukka.presentaion.ui.components.uiutils

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.window.layout.DisplayFeature
import com.arjental.taimukka.presentaion.ui.components.app.ContentType
import com.arjental.taimukka.presentaion.ui.components.app.NavigationContentPosition
import com.arjental.taimukka.presentaion.ui.components.app.NavigationType

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}

val LocalDisplayFeatures = staticCompositionLocalOf<List<DisplayFeature>> {
    noLocalProvidedFor("LocalDisplayFeatures")
}

val LocalComponentType = staticCompositionLocalOf<ContentType> {
    noLocalProvidedFor("LocalComponentType")
}

val LocalNavigationType = staticCompositionLocalOf<NavigationType> {
    noLocalProvidedFor("LocalNavigationType")
}

val LocalNavigationContentPosition = staticCompositionLocalOf<NavigationContentPosition> {
    noLocalProvidedFor("LocalNavigationContentPosition")
}



