package com.arjental.taimukka.domain.repos

import com.arjental.taimukka.data.stats.UsageStatsManager
import com.arjental.taimukka.entities.domain.stats.UserStats
import javax.inject.Inject

interface UserStatsRepository {
    suspend fun collectStats(): UserStats
}

class UserStatsRepositoryImpl @Inject constructor(
    private val usageStatsManager: UsageStatsManager
): UserStatsRepository {

    override suspend fun collectStats(): UserStats {
        //get from cash with last timestamp
        val stats = usageStatsManager.launch()

        //save to cash with timestamp
        stats.screenInteractiveEvents


        return UserStats(false)
    }

}

