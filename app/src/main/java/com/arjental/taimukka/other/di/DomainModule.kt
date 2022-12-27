package com.arjental.taimukka.other.di

import com.arjental.taimukka.data.stats.UsageStatsManager
import com.arjental.taimukka.data.stats.UsageStatsManagerImpl
import com.arjental.taimukka.domain.repos.UserStatsRepository
import com.arjental.taimukka.domain.repos.UserStatsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindUsageStatsManager(usageStatsManagerImpl: UsageStatsManagerImpl): UsageStatsManager

    @Binds
    fun bindUserStatsRepository(userStatsRepositoryImpl: UserStatsRepositoryImpl): UserStatsRepository

}

