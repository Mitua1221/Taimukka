package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.domain.repos.UserStatsRepository
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import javax.inject.Inject

class ApplicationStatsUC @Inject constructor(
    private val userStatsRepository: UserStatsRepository
) {

    /**
     * @param timeline defined timeline that selected in a filters
     * @param appPackage defined package of application witch need to be shown
     */
    suspend fun applicationStats(timeline: Timeline, appPackage: String) = userStatsRepository.applicationStats(timeline = timeline, appPackage = appPackage)

}