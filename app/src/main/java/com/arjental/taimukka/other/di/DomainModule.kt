package com.arjental.taimukka.other.di

import com.arjental.taimukka.domain.repos.UsageStatsManager
import com.arjental.taimukka.domain.repos.UsageStatsManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindUsageStatsManager(usageStatsManagerImpl: UsageStatsManagerImpl): UsageStatsManager

}

