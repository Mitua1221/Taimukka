package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.entities.data.cash.UpdateTimes
import javax.inject.Inject

interface UpdateTimesHolder {
    suspend fun updatedAppList(time: Long)
    suspend fun updatedUsageStats(time: Long)
    suspend fun getUpdatedAppList(): Long
    suspend fun getUpdatedUsageStats(): Long
}

class UpdateTimesHolderImpl @Inject constructor(
    private val database: Database
) : UpdateTimesHolder {

    private val UPDATE_APP_LIST = "UPDATE_APP_LIST"
    private val UPDATE_USAGE_STATS = "UPDATE_USAGE_STATS"

    private val updateTime = database.updatesTime()

    override suspend fun updatedAppList(time: Long) =
        updateTime.setUpdateTime(
            UpdateTimes(
                updatedName = UPDATE_APP_LIST,
                updatedTime = time
            )
        )


    override suspend fun updatedUsageStats(time: Long) =
        updateTime.setUpdateTime(
            UpdateTimes(
                updatedName = UPDATE_USAGE_STATS,
                updatedTime = time
            )
        )


    override suspend fun getUpdatedAppList(): Long =
        updateTime.getUpdateTime(
            updateTimeKey = UPDATE_APP_LIST
        )?.updatedTime ?: 0


    override suspend fun getUpdatedUsageStats(): Long =
        updateTime.getUpdateTime(
            updateTimeKey = UPDATE_USAGE_STATS
        )?.updatedTime ?: 0

}