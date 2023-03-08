package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.domain.repos.UserStatsRepository
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import javax.inject.Inject

class ApplicationsStatsUC @Inject constructor(
    private val userStatsRepository: UserStatsRepository
) {

    /**
     * @param timeline defined timeline that selected in a filters
     * @param selectionType defined type that was selected in a filters by user
     */
    suspend fun applicationsStats(timeline: Timeline, selectionType: SelectionType) = userStatsRepository.applicationsStats(timeline = timeline, selectionType = selectionType)

}