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
        LaunchedAppDomain(
            appPackage = app.appPackage,
            appName = app.appName,
            nonSystem = app.nonSystem,
            appCategory = app.appCategory,
            launches = app.launches.map {
                LaunchedAppTimeMarkDomain(
                    from = it.first,
                    to = it.second
                )
            },
            notificationsMarks = app.notifications.map {
                NotificationsReceivedDomain(time = it)
            }
        )
    }
}