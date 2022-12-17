package com.arjental.taimukka.di

import com.arjental.taimukka.repos.user_stats_manager.UsageStatsManager
import com.arjental.taimukka.repos.user_stats_manager.UsageStatsManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindUsageStatsManager(usageStatsManagerImpl: UsageStatsManagerImpl): UsageStatsManager

}

