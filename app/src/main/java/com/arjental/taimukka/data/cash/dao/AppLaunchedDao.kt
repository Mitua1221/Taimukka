package com.arjental.taimukka.data.cash.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arjental.taimukka.entities.data.cash.AppLaunched

@Dao
interface AppLaunchedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setLaunched(idLaunched: AppLaunched)

    @Query("SELECT * FROM app_launched WHERE `app_launched_key`=(:launchedKey)")
    suspend fun getLaunched(launchedKey: String): AppLaunched?

}