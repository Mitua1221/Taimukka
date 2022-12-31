package com.arjental.taimukka.other.di

import com.arjental.taimukka.data.cash.holders.ApplicationsStatsHolder
import com.arjental.taimukka.data.cash.holders.ApplicationsStatsHolderImpl
import com.arjental.taimukka.data.cash.holders.UpdateTimesHolder
import com.arjental.taimukka.data.cash.holders.UpdateTimesHolderImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [DatabaseModule::class]
)
interface DataModule {

    @Binds
    fun bindUpdateTimesHolder(updateTimesHolderImpl: UpdateTimesHolderImpl): UpdateTimesHolder

    @Binds
    fun bindApplicationsStatsHolder(applicationsStatsHolderImpl: ApplicationsStatsHolderImpl): ApplicationsStatsHolder

}

