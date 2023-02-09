package com.arjental.taimukka.other.di

import androidx.lifecycle.ViewModelProvider
import com.arjental.taimukka.other.utils.factories.viewmodel.TaimukkaViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelFactoryModule {

    @Binds
    @Singleton
    fun bindsTaimukkaViewModelFactory(factory: TaimukkaViewModelFactory): ViewModelProvider.Factory

}