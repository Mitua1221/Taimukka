package com.arjental.taimukka.entities.domain.stats

import com.arjental.taimukka.entities.data.cash.ApplicationInfoCash
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash
import com.arjental.taimukka.entities.data.cash.ApplicationTimeMarksCash

data class LaunchedAppDomain(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    val launches: List<LaunchedAppTimeMarkDomain>,
)

data class LaunchedAppTimeMarkDomain(
    val from: Long,
    val to: Long,
)

suspend fun List<LaunchedAppDomain>.toCash(): List<ApplicationStatsCash> = this.map {
    ApplicationStatsCash(
        appInfo = ApplicationInfoCash(
            appPackage = it.appPackage,
            appName = it.appName,
            nonSystem = it.nonSystem,
        ),
        timeMarks = it.launches.map { launch ->
            ApplicationTimeMarksCash(
                key = it.appPackage+launch.from,
                from = launch.from,
                to = launch.to,
                appPackage = it.appPackage
            )
        }
    )
}
