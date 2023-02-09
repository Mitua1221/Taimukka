package com.arjental.taimukka.presentaion.ui.screens.tabs.settings.screen_parts

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import javax.inject.Inject

class AuthorizationPart : ScreenPart() {

    @Composable
    override fun TContent() =
        AuthorizationContent()


}

@Composable
fun AuthorizationContent() {

    val v = daggerViewModel<AuthorizationVM>()
    val s = v.collectState().collectAsState().value

    Column {
        (0..s).forEach {
            // item {
            Text(text = it.toString())
            //  }
        }
    }


}

class AuthorizationVM @Inject constructor(
    private val dispatchers: TDispatcher,
) : TViewModel<Int, Int>(
    initialState = 30, dispatchers = dispatchers,
) {

    init {
        println("AuthorizationVM init")
    }

    override fun onCleared() {
        println("AuthorizationVM cleared")
        super.onCleared()
    }

}