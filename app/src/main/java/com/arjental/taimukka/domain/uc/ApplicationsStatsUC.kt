package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.domain.repos.UserStatsRepository
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import javax.inject.Inject

class ApplicationsStatsUC @Inject constructor(
    private val userStatsRepository: UserStatsRepository
) {

    suspend fun applicationsStats(timeline: Timeline) = userStatsRepository.applicationsStats(timeline = timeline)

}