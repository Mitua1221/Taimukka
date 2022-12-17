package com.arjental.taimukka.di

import com.arjental.taimukka.TaimukkaActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ScreenModule {

    @ContributesAndroidInjector
    abstract fun bindTaimukkaActivity(): TaimukkaActivity

}