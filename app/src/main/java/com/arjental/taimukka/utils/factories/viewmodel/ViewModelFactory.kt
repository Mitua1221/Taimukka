package ru.taxcom.taxcomkit.utils.factories.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return viewModels[modelClass]?.get() as T
        } catch (e: NullPointerException) {
            throw IllegalStateException("You forget to add !! ${modelClass.javaClass.canonicalName} !! to Dagger graph")
        }
    }

}