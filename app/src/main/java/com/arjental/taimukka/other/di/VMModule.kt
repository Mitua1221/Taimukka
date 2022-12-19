package com.arjental.taimukka.other.di

import androidx.lifecycle.ViewModel
import com.arjental.taimukka.presentaion.ui.screens.main.MainViewModel
import com.arjental.taimukka.other.utils.factories.viewmodel.ViewModelKey
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