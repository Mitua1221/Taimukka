package com.arjental.taimukka.other.utils.resources

import android.content.Context
import kotlinx.datetime.DateTimePeriod

fun formatMillisToPresentation(context: Context, millis: Long): String {
    val period = DateTimePeriod(seconds = (millis / 1000).toInt())
    when {
        period.years != 0 -> {
            if (period.days == 0) {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_year, period.years.toString())
            } else {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_year_day, period.years.toString(), period.days.toString())
            }
        }
        period.days != 0 -> {
            if (period.hours == 0) {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_day, period.days.toString())
            } else {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_day_hours, period.days.toString(), period.hours.toString())
            }
        }
        period.hours != 0 -> {
            if (period.minutes == 0) {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_hours, period.hours.toString())
            } else {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_hours_minutes, period.hours.toString(), period.minutes.toString())
            }
        }
        period.minutes != 0 -> {
            if (period.seconds == 0) {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_minutes, period.minutes.toString())
            } else {
                return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_minutes_seconds, period.minutes.toString(), period.seconds.toString())
            }
        }
        period.seconds != 0 -> {
            return context.resources.getString(com.arjental.taimukka.R.string.tilmeline_seconds, period.seconds.toString())
        }
        else -> return ""
    }
}