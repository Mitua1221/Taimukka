package com.arjental.taimukka.other.di

import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.dispatchers.TDispatcherImpl
import dagger.Binds
import dagger.Module


@Module
interface CoroutineModule {
    @Binds
    fun bindTDispatcher(TDispatcherImpl: TDispatcherImpl): TDispatcher
}

