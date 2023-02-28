package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.entities.pierce.timeline.Timeline
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * For getting selected timeline all any screen
 */
class TimelineUC @Inject constructor(
    private val settings: SettingsUC,
) {
    suspend fun getTimeline(): Flow<Timeline> = settings.getTimeline()
    suspend fun changeTimeline(timeline: Timeline) = settings.setTimeline(timeline)
}