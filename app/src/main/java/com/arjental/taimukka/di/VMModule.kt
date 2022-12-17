package com.arjental.taimukka.di

import androidx.lifecycle.ViewModel
import com.arjental.taimukka.ui.screens.main.MainViewModel
import com.arjental.taimukka.utils.factories.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [ViewModelFactoryModule::class]
)
interface VMModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}