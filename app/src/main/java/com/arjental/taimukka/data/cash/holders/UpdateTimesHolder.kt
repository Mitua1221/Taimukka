package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.entities.data.cash.UpdateTimes
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface UpdateTimesHolder {
    /**
     * Save updates of app's list
     * @param from may be null because we dont update *from* value if launch isnt first
     */
    suspend fun updatedAppList(from: Long?, to: Long)

    /**
     * Get updates of app's list, if there was no updates, returns zeros
     * @param [Pair] first - from, second - to
     */
    suspend fun getAppCacheTimestamps(): Pair<Long, Long>
    suspend fun updatedUsageStats(time: Long)

    suspend fun getUpdatedUsageStats(): Long
}

class UpdateTimesHolderImpl @Inject constructor(
    private val database: Database
) : UpdateTimesHolder {

    private val UPDATE_APP_LIST_FROM = "UPDATE_APP_LIST_FROM"
    private val UPDATE_APP_LIST_TO = "UPDATE_APP_LIST_TO"

    private val UPDATE_USAGE_STATS = "UPDATE_USAGE_STATS"

    private val updateTime = database.updatesTime()

    override suspend fun updatedAppList(from: Long?, to: Long) {
        if (from != null) updateTime.setUpdateTime(UpdateTimes(updatedName = UPDATE_APP_LIST_FROM, updatedTime = from))
        updateTime.setUpdateTime(UpdateTimes(updatedName = UPDATE_APP_LIST_TO, updatedTime = to))
    }

    override suspend fun getAppCacheTimestamps(): Pair<Long, Long> = coroutineScope {
        val from = async { updateTime.getUpdateTime(updateTimeKey = UPDATE_APP_LIST_FROM)?.updatedTime ?: 0 }
        val to = async { updateTime.getUpdateTime(updateTimeKey = UPDATE_APP_LIST_TO)?.updatedTime ?: 0 }
        return@coroutineScope Pair(first = from.await(), second = to.await())
    }

    override suspend fun updatedUsageStats(time: Long) =
        updateTime.setUpdateTime(
            UpdateTimes(
                updatedName = UPDATE_USAGE_STATS,
                updatedTime = time
            )
        )

    override suspend fun getUpdatedUsageStats(): Long =
        updateTime.getUpdateTime(
            updateTimeKey = UPDATE_USAGE_STATS
        )?.updatedTime ?: 0

}