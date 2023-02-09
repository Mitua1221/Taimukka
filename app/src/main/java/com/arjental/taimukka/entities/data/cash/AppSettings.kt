package com.arjental.taimukka.entities.data.cash

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
class AppSettings(
    @ColumnInfo(name = "app_settings_key") @PrimaryKey val settingsKey: String,
    @ColumnInfo(name = "app_settings_value") val settingsValue: String
)
