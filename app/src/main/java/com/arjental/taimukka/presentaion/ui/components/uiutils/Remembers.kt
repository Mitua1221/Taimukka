package com.arjental.taimukka.presentaion.ui.components.uiutils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberTCoroutineScope(
    onMainThread: Boolean = false,
    dispatcher: TDispatcher = LocalDispatchers.current
): CoroutineScope {
    return rememberCoroutineScope { if (onMainThread) dispatcher.main else dispatcher.default }
}