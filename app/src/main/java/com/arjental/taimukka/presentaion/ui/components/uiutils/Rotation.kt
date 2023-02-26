package com.arjental.taimukka.presentaion.ui.components.uiutils

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.collectLatest

/**
 * Rotate state
 *
 */


//val rotation = smoothRotation(-state.azimuth)
//
//
//val animatedRotation by animateFloatAsState(
//  targetValue = rotation.value,
//  animationSpec = tween(
//    durationMillis = 400,
//    easing = LinearOutSlowInEasing
//  )
//)

@Composable
fun smoothRotation(rotation: Float): MutableState<Float> {
    val storedRotation = remember { mutableStateOf(rotation) }

    // Sample data
    // current angle 340 -> new angle 10 -> diff -330 -> +30
    // current angle 20 -> new angle 350 -> diff 330 -> -30
    // current angle 60 -> new angle 270 -> diff 210 -> -150
    // current angle 260 -> new angle 10 -> diff -250 -> +110

    LaunchedEffect(rotation) {
        snapshotFlow { rotation }
            .collectLatest { newRotation ->
                val diff = newRotation - storedRotation.value
                val shortestDiff = when {
                    diff > 180 -> diff - 360
                    diff < -180 -> diff + 360
                    else -> diff
                }
                storedRotation.value = storedRotation.value + shortestDiff
            }
    }

    return storedRotation
}