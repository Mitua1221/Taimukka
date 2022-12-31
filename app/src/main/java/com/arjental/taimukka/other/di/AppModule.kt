package com.arjental.taimukka.other.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        VMModule::class,
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

}