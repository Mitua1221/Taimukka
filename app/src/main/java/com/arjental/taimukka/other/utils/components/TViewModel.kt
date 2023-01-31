package com.arjental.taimukka.other.utils.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

open class TViewModel<State, Effect> @Inject constructor(
    initialState: State,
    private val dispatchers: TDispatcher
) : ViewModel() {

    val ViewModel.viewModelDefaultScope: CoroutineScope by lazy {
        viewModelScope.plus(dispatchers.default + handler)
    }

    private val state = MutableStateFlow(initialState)

    private val _collectEffect = Channel<Effect>(capacity = Channel.BUFFERED, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val collectEffect = _collectEffect.receiveAsFlow()

    private val handler = CoroutineExceptionHandler { context, exception ->
        throw exception
    }

    fun TViewModel<State, Effect>.launch(
        handler: CoroutineExceptionHandler = this.handler,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelDefaultScope.launch { block() }


    private suspend fun postEffect(effect: Effect) = _collectEffect.send(effect)

    open fun modifyState(newState: State) { state.update { newState } }
    open fun modifyState(function: (State) -> State) { state.update(function) }
    open fun collectState(): StateFlow<State> = state.asStateFlow()

    fun stateValue() = state.value

}