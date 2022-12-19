package com.arjental.taimukka.other.di

import androidx.lifecycle.ViewModelProvider
import com.arjental.taimukka.other.utils.factories.viewmodel.TaimukkaViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindsTaimukkaViewModelFactory(factory: TaimukkaViewModelFactory): ViewModelProvider.Factory

}