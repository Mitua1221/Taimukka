package com.arjental.taimukka.other.utils.factories.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cafe.adriel.voyager.androidx.AndroidScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalTActivity
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalTScreen

//https://medium.com/@mikhail_zhalskiy/injecting-savedstatehandle-into-viewmodel-using-dagger-2-and-jetpack-compose-30b34df9ffd1
//@Composable
//inline fun <reified VM : ViewModel> daggerViewModel(): VM {
//    val factory = getViewModelFactory()
//    return viewModel{
//        //val savedStateHandle = createSavedStateHandle()
//        factory.create(VM::class.java)
//    }
//}

enum class VMOwner {
    ACTIVITY,
    CURRENT_COMPOSABLE
}

@Composable
public inline fun <reified VM : ViewModel> daggerActivityViewModel(
    factory: ViewModelProvider.Factory = getViewModelFactory(),
) = daggerViewModel<VM>(
    owner = VMOwner.ACTIVITY,
    factory = getViewModelFactory(),
)

@Composable
public inline fun <reified VM : ViewModel> daggerViewModel(
    owner: VMOwner = VMOwner.CURRENT_COMPOSABLE,
    factory: ViewModelProvider.Factory = getViewModelFactory(),
): VM {
    val context = LocalTActivity.current
    val screen = LocalTScreen.current
    return remember(key1 = VM::class) {
        val lifecycleOwner =
            if (owner == VMOwner.ACTIVITY) {
                context
            } else {
                (screen as? ScreenLifecycleProvider)
                    ?.getLifecycleOwner() as? AndroidScreenLifecycleOwner
                    ?: throw IllegalStateException("composable scope viewmodel requested not from composable screen")
            }
//        val factory = VoyagerHiltViewModelFactories.getVoyagerFactory(
//            activity = context,
//            owner = lifecycleOwner,
//            delegateFactory = viewModelProviderFactory ?: lifecycleOwner.defaultViewModelProviderFactory
//        )
        val provider = ViewModelProvider(
            store = lifecycleOwner.viewModelStore,
            factory = factory,
            defaultCreationExtras = lifecycleOwner.defaultViewModelCreationExtras
        )
        provider[VM::class.java]
    }
}

@Composable
@PublishedApi
internal fun getViewModelFactory(): ViewModelProvider.Factory {
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
public fun SetViewModelFactory(
    viewModelFactory: ViewModelProvider.Factory,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory,
        content = content
    )
}