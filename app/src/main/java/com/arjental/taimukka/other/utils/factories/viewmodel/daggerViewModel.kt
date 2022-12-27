package com.arjental.taimukka.other.utils.factories.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(): VM {
    val factory = getViewModelFactory()
    return viewModel {
        //val savedStateHandle = createSavedStateHandle()
        factory.create(VM::class.java)
    }
}

@Composable
@PublishedApi
internal fun getViewModelFactory(): ViewModelProvider.Factory  {
    return checkNotNull(LocalViewModelFactory.current) {
        "No ViewModelFactory was provided via LocalViewModelFactory"
    }
}

public object LocalViewModelFactory {
    private val LocalViewModelFactory =
        compositionLocalOf<ViewModelProvider.Factory?> { null }

    public val current: ViewModelProvider.Factory?
        @Composable
        get() = LocalViewModelFactory.current

    public infix fun provides(viewModelFactory: ViewModelProvider.Factory):
            ProvidedValue<ViewModelProvider.Factory?> {
        return LocalViewModelFactory.provides(viewModelFactory)
    }
}

@Composable
public fun Inject(
    viewModelFactory: ViewModelProvider.Factory,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory,
        content = content
    )
}