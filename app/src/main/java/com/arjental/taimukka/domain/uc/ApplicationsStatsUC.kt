package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.domain.repos.UserStatsRepository
import javax.inject.Inject

class ApplicationsStatsUC @Inject constructor(
    private val userStatsRepository: UserStatsRepository
) {

    suspend fun collect() = userStatsRepository.applicationsStats()

}