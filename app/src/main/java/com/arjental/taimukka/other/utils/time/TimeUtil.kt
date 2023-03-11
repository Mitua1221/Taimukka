package com.arjental.taimukka.other.utils.time

import com.arjental.taimukka.entities.pierce.timeline.TimelineType
import kotlinx.datetime.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object TimeUtil {

    fun getCurrentTimeMillis() = Clock.System.now().toEpochMilliseconds()

    /**
     * Returns timestamp of end of a current day
     */
    fun getStartOfTomorrowTimestamp(): Long {
        val tz = TimeZone.currentSystemDefault()
        return Clock.System.todayIn(tz)
            .atStartOfDayIn(tz)
            .plus(1 , DateTimeUnit.DAY, tz)
            .toEpochMilliseconds()
    }

    fun getPreviousTimeFromTimelineType(timelineType: TimelineType): Long {
        val systemTZ = TimeZone.currentSystemDefault()
        val today = Clock.System.todayIn(systemTZ).atStartOfDayIn(systemTZ)
        return when (timelineType) {
            TimelineType.DAY ->  today.minus(1, DateTimeUnit.DAY, systemTZ).toEpochMilliseconds()
            TimelineType.MONTH -> today.minus(1, DateTimeUnit.MONTH, systemTZ).toEpochMilliseconds()
            TimelineType.WEEK -> today.minus(1, DateTimeUnit.WEEK, systemTZ).toEpochMilliseconds()
            TimelineType.CUSTOM -> error("you cant get timeline for custom timeline")
        }
    }

    /**
     * @param timestamp in millis
     * @param timelineType in type we want to decrease
     * @param quality we want to decrease
     */
    fun decreaseFromTime(timestamp: Long, timelineType: TimelineType, quality: Long): Long {
        val toRemove = Instant.fromEpochMilliseconds(timestamp)
        val systemTZ = TimeZone.currentSystemDefault()

        return when (timelineType) {
            TimelineType.DAY ->  toRemove.minus(quality, DateTimeUnit.DAY, systemTZ).toEpochMilliseconds()
            TimelineType.MONTH -> toRemove.minus(quality, DateTimeUnit.MONTH, systemTZ).toEpochMilliseconds()
            TimelineType.WEEK -> toRemove.minus(quality, DateTimeUnit.WEEK, systemTZ).toEpochMilliseconds()
            TimelineType.CUSTOM -> error("you cant get timeline for custom timeline")
        }
    }

}