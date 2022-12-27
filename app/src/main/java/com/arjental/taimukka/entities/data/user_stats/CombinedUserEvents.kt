package com.arjental.taimukka.entities.data.user_stats

/**
 * @property launchedAppsMap when app launched and how much it runs on device with timestamps of each run
 * @property screenInteractiveEvents events with screen enabled/disabled, pair.first is [UsageEvents.Event], pair.second is timestamp of action
 */

class CombinedUserEvents(
    val launchedAppsMap: Map<String, LaunchedApp>,
    val screenInteractiveEvents: List<Pair<Int, Long>>, //
)