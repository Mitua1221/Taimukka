package com.arjental.taimukka.other.utils.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class  TViewModel <State> () : ViewModel() {

    abstract fun collect(): StateFlow<State>

}