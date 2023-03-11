package com.arjental.taimukka.entities.domain.stats

import com.arjental.taimukka.other.utils.annotataions.Category

/**

 */
data class LaunchedAppDetailedDomain(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    @Category val appCategory: Int?,

    val launches: List<LaunchedAppTimeMarkDomain>,
    val screenTimeMillis: Long,
    val screenTimePercentage: Float = 0f,

    val notificationsMarks: List<NotificationsReceivedDomain>,
    val notificationsQuality: Int,
    val notificationsPercentage: Float = 0f,

    val seancesQuality: Int,
    val seancesPercentage: Float = 0f,


    )

fun LaunchedAppDomain.toDetailed(
    screenTimePercentage: Float,
    notificationsPercentage: Float,
    seancesPercentage: Float,
    notificationsQuality: Int,
    seancesQuality: Int,
    screenTimeMillis: Long,
): LaunchedAppDetailedDomain {
    return LaunchedAppDetailedDomain(
        appPackage = appPackage,
        appName = appName,
        nonSystem = nonSystem,
        appCategory = appCategory,
        launches = launches,
        screenTimePercentage = screenTimePercentage,
        screenTimeMillis = screenTimeMillis,
        notificationsMarks = notificationsMarks,
        notificationsQuality = notificationsQuality,
        notificationsPercentage = notificationsPercentage,
        seancesQuality = seancesQuality,
        seancesPercentage = seancesPercentage,
    )
}
