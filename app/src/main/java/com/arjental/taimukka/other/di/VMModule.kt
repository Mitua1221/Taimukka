package com.arjental.taimukka.other.di

import androidx.lifecycle.ViewModel
import com.arjental.taimukka.presentaion.ui.screens.main.MainViewModel
import com.arjental.taimukka.other.utils.factories.viewmodel.ViewModelKey
import com.arjental.taimukka.presentaion.ui.screens.app_list.AppListVM
import com.arjental.taimukka.presentaion.ui.screens.splash.SplashVM
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

    @Binds
    @IntoMap
    @ViewModelKey(AppListVM::class)
    fun bindAppListVM(viewModel: AppListVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashVM::class)
    fun splashVM(viewModel: SplashVM): ViewModel

}