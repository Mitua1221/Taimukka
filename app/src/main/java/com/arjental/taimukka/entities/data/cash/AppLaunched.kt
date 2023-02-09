package com.arjental.taimukka.entities.data.cash

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_launched")
class AppLaunched(
    @ColumnInfo(name = "app_launched_key") @PrimaryKey val launchedKey: String,
    @ColumnInfo(name = "app_launched_value") val launched: Boolean
)
