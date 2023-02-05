package com.arjental.taimukka.presentaion.ui.components.app

import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.window.layout.FoldingFeature
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalContentType
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalNavigationType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(
        val hingePosition: Rect
    ) : DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ) : DevicePosture
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}

/**
 * Different type of navigation supported by app depending on device size and state.
 */
enum class NavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

@Composable
fun isBottomNav(): Boolean = LocalNavigationType.current == NavigationType.BOTTOM_NAVIGATION

@Composable
fun isRailNav(): Boolean = LocalNavigationType.current == NavigationType.NAVIGATION_RAIL

@Composable
fun isDrawerNav(): Boolean = LocalNavigationType.current == NavigationType.PERMANENT_NAVIGATION_DRAWER

/**
 * Different position of navigation content inside Navigation Rail, Navigation Drawer depending on device size and state.
 */
enum class NavigationContentPosition {
    TOP, CENTER
}

/**
 * App Content shown depending on device size and state.
 */
enum class ContentType {
    SINGLE_PANE, DUAL_PANE
}

@Composable
fun isSingle(): Boolean = !isDual()

@Composable
fun isDual(): Boolean {
    return LocalContentType.current == ContentType.DUAL_PANE
}