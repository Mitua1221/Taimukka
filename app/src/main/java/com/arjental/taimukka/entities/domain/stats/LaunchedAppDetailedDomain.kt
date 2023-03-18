package com.arjental.taimukka.entities.domain.stats

import com.arjental.taimukka.other.utils.annotataions.Category

/**
 * Provides detailed information about application for application details screen
 */
data class LaunchedAppDetailedDomain(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    @Category val appCategory: Int?,

    val launches: List<LaunchedAppTimeMarkDomain>,
    val screenTimeMillis: Long,
    val screenTimePercentage: Float = 0f,

    val notificationsMarks: List<Long>,
    val notificationsQuality: Int,
    val notificationsPercentage: Float = 0f,

    val notificationsSeenQuality: Int,

    val seancesQuality: Int,
    val seancesPercentage: Float = 0f,

//    val notificationsSeen: Int,
//    val foregroundServicesStarted: Int = 71
)

fun LaunchedAppDomain.toDetailed(
    screenTimePercentage: Float,
    notificationsPercentage: Float,
    seancesPercentage: Float,
    notificationsQuality: Int,
    seancesQuality: Int,
    screenTimeMillis: Long,
    notificationsSeenQuality: Int,
): LaunchedAppDetailedDomain {
    return LaunchedAppDetailedDomain(
        appPackage = appPackage,
        appName = appName,
        nonSystem = nonSystem,
        appCategory = appCategory,
        launches = launches,
        screenTimePercentage = screenTimePercentage,
        screenTimeMillis = screenTimeMillis,
        notificationsMarks = notificationsReceived,
        notificationsQuality = notificationsQuality,
        notificationsPercentage = notificationsPercentage,
        seancesQuality = seancesQuality,
        seancesPercentage = seancesPercentage,
        notificationsSeenQuality = notificationsSeenQuality,
    )
}
