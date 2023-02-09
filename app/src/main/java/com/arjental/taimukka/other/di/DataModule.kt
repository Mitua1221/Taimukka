package com.arjental.taimukka.other.di

import com.arjental.taimukka.data.cash.holders.*
import dagger.Binds
import dagger.Module

@Module(
    includes = [DatabaseModule::class]
)
interface DataModule {

    @Binds
    fun bindAppLaunchedHolder(appLaunchedHolder: AppLaunchedHolderImpl): AppLaunchedHolder

    @Binds
    fun bindUpdateTimesHolder(updateTimesHolderImpl: UpdateTimesHolderImpl): UpdateTimesHolder

    @Binds
    fun bindApplicationsStatsHolder(applicationsStatsHolderImpl: ApplicationsStatsHolderImpl): ApplicationsStatsHolder

    @Binds
    fun bindSettingsHolder(settingsHolderImpl: SettingsHolderImpl): SettingsHolder

}

