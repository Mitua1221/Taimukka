package com.arjental.taimukka.entities.pierce.timeline

import kotlinx.collections.immutable.persistentListOf

/**
 * Its not stored in cash, only creates from cash values
 */

data class Timeline(
    val timelineType: TimelineType,
    val from: Long? = null,
    val to: Long? = null,
)

enum class TimelineType {
    DAY, WEEK, MONTH, CUSTOM
}

class TimelineTypeMax() {
    val max = TimelineType.MONTH
}


