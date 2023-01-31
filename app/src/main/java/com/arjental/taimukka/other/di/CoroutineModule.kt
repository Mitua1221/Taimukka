package com.arjental.taimukka.other.di

import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.dispatchers.TDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import javax.inject.Singleton


@Module
interface CoroutineModule {
    @Binds
    @Singleton
    fun bindTDispatcher(TDispatcherImpl: TDispatcherImpl): TDispatcher

}

