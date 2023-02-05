package com.arjental.taimukka.data.cash.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arjental.taimukka.entities.data.cash.AppSettings

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettingsItem(item: AppSettings)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(list: List<AppSettings>)

    @Query("SELECT * FROM app_settings WHERE `app_settings_key`=(:settingKey)")
    suspend  fun getSettingsItem(settingKey: String): AppSettings?

    @Query("SELECT * FROM app_settings")
    suspend  fun getSettings(): List<AppSettings>

}