package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.data.stats.UsageStatsManager
import com.arjental.taimukka.domain.repos.UserStatsRepository
import javax.inject.Inject

class CollectUserStatsUC @Inject constructor(
    private val userStatsRepository: UserStatsRepository
) {

    suspend fun collect() {
        userStatsRepository.collectStats()
    }

}