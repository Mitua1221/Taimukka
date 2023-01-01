package com.arjental.taimukka.entities.data.user_stats

import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppTimeMarkDomain

class LaunchedApp(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    val launches: MutableList<Pair<Long, Long>>,
)

suspend fun List<LaunchedApp>.toDomain(): List<LaunchedAppDomain> {
    return this.map { app ->
        LaunchedAppDomain(
            appPackage = app.appPackage,
            appName = app.appName,
            nonSystem = app.nonSystem,
            launches = app.launches.map {
                LaunchedAppTimeMarkDomain(
                    from = it.first,
                    to = it.second
                )
            }
        )
    }
}