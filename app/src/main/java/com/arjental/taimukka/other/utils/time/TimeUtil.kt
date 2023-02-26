package com.arjental.taimukka.other.utils.time

import com.arjental.taimukka.entities.pierce.timeline.TimelineType
import kotlinx.datetime.*

object TimeUtil {

    fun getCurrentTimeMillis() = Clock.System.now().toEpochMilliseconds()

    fun getPreviousTimeFromTimelineType(timelineType: TimelineType): Long {
        val systemTZ = TimeZone.currentSystemDefault()
        return when (timelineType) {
            TimelineType.DAY ->  Clock.System.now().minus(1, DateTimeUnit.DAY, systemTZ).toEpochMilliseconds()
            TimelineType.MONTH -> Clock.System.now().minus(1, DateTimeUnit.MONTH, systemTZ).toEpochMilliseconds()
            TimelineType.WEEK -> Clock.System.now().minus(1, DateTimeUnit.WEEK, systemTZ).toEpochMilliseconds()
            TimelineType.CUSTOM -> error("you cant get timeline for custom timeline")
        }
    }


}