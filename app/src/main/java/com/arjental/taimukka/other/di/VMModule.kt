package com.arjental.taimukka.other.di

import androidx.lifecycle.ViewModel
import com.arjental.taimukka.other.utils.factories.viewmodel.ViewModelKey
import com.arjental.taimukka.presentaion.ui.screens.splash.SplashVM
import com.arjental.taimukka.presentaion.ui.screens.tabs.app_list.AppListVM
import com.arjental.taimukka.presentaion.ui.screens.tabs.settings.SettingsVM
import com.arjental.taimukka.presentaion.ui.screens.tabs.stats.StatsVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [ViewModelFactoryModule::class]
)
interface VMModule {

    @Binds
    @IntoMap
    @ViewModelKey(StatsVM::class)
    fun bindMainViewModel(viewModel: StatsVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AppListVM::class)
    fun bindAppListVM(viewModel: AppListVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashVM::class)
    fun splashVM(viewModel: SplashVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsVM::class)
    fun settingsVM(viewModel: SettingsVM): ViewModel


}