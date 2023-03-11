package com.arjental.taimukka.entities.data.user_stats

import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppTimeMarkDomain
import com.arjental.taimukka.entities.domain.stats.NotificationsReceivedDomain
import com.arjental.taimukka.other.utils.annotataions.Category

/**
 * Only for [UserStatsManager] entities
 * @param notifications just holding notification timestamp
 */
class LaunchedApp(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    val launches: MutableList<Pair<Long, Long>>,
    val notifications: MutableList<Long> = mutableListOf(),
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
    notificationsMarks = this.notifications.map {
        NotificationsReceivedDomain(time = it)
    }
)