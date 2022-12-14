package com.arjental.taimukka.di

import com.arjental.taimukka.timeholder.holder.UsageStatsManager
import com.arjental.taimukka.timeholder.holder.UsageStatsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityComponent::class)
interface DomainModule {

    @Binds
    fun bindUsageStatsManager(
        usageStatsManagerImpl: UsageStatsManagerImpl
    ): UsageStatsManager

}

