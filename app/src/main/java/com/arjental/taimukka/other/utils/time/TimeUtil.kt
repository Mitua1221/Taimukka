package com.arjental.taimukka.other.utils.time

import kotlinx.datetime.Clock

object TimeUtil {

    fun getCurrentTimeMillis() = Clock.System.now().toEpochMilliseconds()

}