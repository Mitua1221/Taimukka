package com.arjental.taimukka.data.cash

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arjental.taimukka.data.cash.dao.ApplicationsStatsDao
import com.arjental.taimukka.data.cash.dao.UpdateTimesDao
import com.arjental.taimukka.entities.data.cash.ApplicationInfoCash
import com.arjental.taimukka.entities.data.cash.ApplicationTimeMarksCash
import com.arjental.taimukka.entities.data.cash.UpdateTimes

@Database(
    entities = [
        ApplicationInfoCash::class,
        ApplicationTimeMarksCash::class,
        UpdateTimes::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = [],
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class Database : RoomDatabase() {

    abstract fun updatesTime(): UpdateTimesDao

    abstract fun applicationsStats(): ApplicationsStatsDao

}