package com.arjental.taimukka.presentaion.ui.components.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.arjental.taimukka.presentaion.ui.components.navigations.BottomNavigationBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaimukkaApplication(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
) {

    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     */
    val navigationType: NavigationType
    val contentType: ContentType

    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ContentType.DUAL_PANE
            } else {
                ContentType.SINGLE_PANE
            }
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ContentType.DUAL_PANE
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
    }

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            NavigationContentPosition.TOP
        }
        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            NavigationContentPosition.CENTER
        }
        else -> {
            NavigationContentPosition.TOP
        }
    }

    NavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationWrapper(
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    when (navigationType) {
        NavigationType.BOTTOM_NAVIGATION -> {
            BottomNavigationBar()
        }
        NavigationType.NAVIGATION_RAIL -> {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalNavigationDrawerContent(
                        selectedDestination = "selectedDestination",
                        navigationContentPosition = navigationContentPosition,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                },
                drawerState = drawerState
            ) {
                AppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    selectedDestination = "selectedDestination",
                ) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        }
        NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            PermanentNavigationDrawer(drawerContent = {
                PermanentNavigationDrawerContent(
                    selectedDestination = "selectedDestination",
                    navigationContentPosition = navigationContentPosition,
                )
            }) {
                AppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    selectedDestination = "selectedDestination",
                )
            }

        }
    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    selectedDestination: String,
    onDrawerClicked: () -> Unit = {}
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            NavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                onDrawerClicked = onDrawerClicked,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {

            //here was a navhost
            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                BottomNavigationBar(
                    selectedDestination = selectedDestination,
                )
            }
        }
    }
}