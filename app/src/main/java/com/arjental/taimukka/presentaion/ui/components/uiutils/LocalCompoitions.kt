package com.arjental.taimukka.presentaion.ui.components.uiutils

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.window.layout.DisplayFeature
import com.arjental.taimukka.TaimukkaActivity
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.presentaion.ui.components.app.ContentType
import com.arjental.taimukka.presentaion.ui.components.app.NavigationContentPosition
import com.arjental.taimukka.presentaion.ui.components.app.NavigationType

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}

val LocalDisplayFeatures = compositionLocalOf<List<DisplayFeature>> {
    noLocalProvidedFor("LocalDisplayFeatures")
}

val LocalContentType = compositionLocalOf<ContentType> {
    noLocalProvidedFor("LocalComponentType")
}

val LocalNavigationType = compositionLocalOf<NavigationType> {
    noLocalProvidedFor("LocalNavigationType")
}

val LocalNavigationContentPosition = compositionLocalOf<NavigationContentPosition> {
    noLocalProvidedFor("LocalNavigationContentPosition")
}

val LocalTActivity = staticCompositionLocalOf<TaimukkaActivity> {
    noLocalProvidedFor("TaimukkaActivity")
}

val LocalTScreen = compositionLocalOf<TScreen?> {
    null
}

val LocalDispatchers = staticCompositionLocalOf<TDispatcher> {
    noLocalProvidedFor("TDispatcher")
}

