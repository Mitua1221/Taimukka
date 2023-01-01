package com.arjental.taimukka.entities.data.cash

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "update_times")
class UpdateTimes(
    @ColumnInfo(name = "update_key") @PrimaryKey val updatedName: String,
    @ColumnInfo(name = "update_time") val updatedTime: Long
)
