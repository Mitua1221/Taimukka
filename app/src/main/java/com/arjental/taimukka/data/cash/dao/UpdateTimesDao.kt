package com.arjental.taimukka.data.cash.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arjental.taimukka.entities.data.cash.UpdateTimes

@Dao
interface UpdateTimesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUpdateTime(updateTime: UpdateTimes)

    @Query("SELECT * FROM update_times WHERE `update_key`=(:updateTimeKey)")
    suspend  fun getUpdateTime(updateTimeKey: String): UpdateTimes?

}