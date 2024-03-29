package com.arjental.taimukka.data.cash

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arjental.taimukka.data.cash.dao.AppLaunchedDao
import com.arjental.taimukka.data.cash.dao.ApplicationsStatsDao
import com.arjental.taimukka.data.cash.dao.SettingsDao
import com.arjental.taimukka.data.cash.dao.UpdateTimesDao
import com.arjental.taimukka.entities.data.cash.*
import dagger.Reusable
import javax.inject.Singleton

@Database(
    entities = [
        ApplicationInfoCash::class,
        ApplicationTimeMarksCash::class,
        UpdateTimes::class,
        AppLaunched::class,
        AppSettings::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = [],
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class Database : RoomDatabase() {

    abstract fun appLaunched(): AppLaunchedDao

    abstract fun settings(): SettingsDao

    abstract fun updatesTime(): UpdateTimesDao

    abstract fun applicationsStats(): ApplicationsStatsDao

}