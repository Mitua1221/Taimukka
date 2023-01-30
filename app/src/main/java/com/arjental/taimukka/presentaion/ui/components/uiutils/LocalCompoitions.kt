package com.arjental.taimukka.presentaion.ui.components.uiutils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.window.layout.DisplayFeature
import com.arjental.taimukka.TaimukkaActivity
import com.arjental.taimukka.other.utils.components.activity.TaimukkaDaggerActivity
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
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

val LocalTActivity = staticCompositionLocalOf<TaimukkaActivity> {
    noLocalProvidedFor("TaimukkaActivity")
}

val LocalDispatchers = staticCompositionLocalOf<TDispatcher> {
    noLocalProvidedFor("TDispatcher")
}

