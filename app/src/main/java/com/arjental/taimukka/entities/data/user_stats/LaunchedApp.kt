package com.arjental.taimukka.entities.data.user_stats

import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppTimeMarkDomain
import com.arjental.taimukka.other.utils.annotataions.Category

/**
 * Only for [UserStatsManager] entities
 * @param notificationsReceived just holding notification timestamp
 */
class LaunchedApp(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    val launches: MutableList<Pair<Long, Long>>,
    val notificationsReceived: MutableList<Long> = mutableListOf(),
    val notificationsSeen: MutableList<Long> = mutableListOf(),
    @Category val appCategory: Int?,
)

suspend fun List<LaunchedApp>.toDomain(): List<LaunchedAppDomain> {
    return this.map { app ->
        app.toDomain()
    }
}

suspend inline fun LaunchedApp.toDomain() = LaunchedAppDomain(
    appPackage = this.appPackage,
    appName = this.appName,
    nonSystem = this.nonSystem,
    appCategory = this.appCategory,
    launches = this.launches.map {
        LaunchedAppTimeMarkDomain(
            from = it.first,
            to = it.second
        )
    },
    notificationsReceived = notificationsReceived,
    notificationsSeen = notificationsSeen
)