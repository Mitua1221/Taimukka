package com.arjental.taimukka.other.utils.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class  TViewModel <State, Effect> () : ViewModel() {

    private val _collectEffect = Channel<Effect>(capacity = Channel.BUFFERED, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val collectEffect = _collectEffect.receiveAsFlow()

    private suspend fun postEffect(effect: Effect) = _collectEffect.send(effect)

    abstract fun collectState(): StateFlow<State>

}