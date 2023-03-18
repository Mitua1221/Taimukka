package com.arjental.taimukka.entities.domain.stats

import com.arjental.taimukka.entities.data.cash.ApplicationForegroundMarksCash
import com.arjental.taimukka.entities.data.cash.ApplicationInfoCash
import com.arjental.taimukka.entities.data.cash.ApplicationNotificationsMarksCash
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash
import com.arjental.taimukka.entities.pierce.selection_type.Type
import com.arjental.taimukka.other.utils.annotataions.Category

/**
 * @param percentage means percents in float like 0.50 - means 50 percents
 * @param realQuality Quality to preview by different [Type], may be in millis for [Type.SCREEN_TIME],
 * in size for [Type.SEANCES] or [Type.NOTIFICATIONS_RECEIVED].
 */
data class LaunchedAppDomain(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    @Category val appCategory: Int?,
    val launches: List<LaunchedAppTimeMarkDomain>,
    val notificationsReceived: List<Long> = emptyList(),
    val notificationsSeen: List<Long> = emptyList(),
    val percentage: Float = 0f,
    val realQuality: Long = 0,
    val type: Type = Type.SCREEN_TIME
)

data class LaunchedAppTimeMarkDomain(
    val from: Long,
    val to: Long,
)

class NotificationsReceivedDomain(
    val time: Long,
)

suspend fun List<LaunchedAppDomain>.toCash(): List<ApplicationStatsCash> = this.map {
    ApplicationStatsCash(
        appInfo = ApplicationInfoCash(
            appPackage = it.appPackage,
            appName = it.appName,
            nonSystem = it.nonSystem,
            appCategory = it.appCategory,
        ),
        foregroundMarks = it.launches.map { launch ->
            ApplicationForegroundMarksCash(
                key = it.appPackage + launch.from,
                from = launch.from, to = launch.to, appPackage = it.appPackage
            )
        },
        notifications = it.notificationsReceived.map { notification ->
            ApplicationNotificationsMarksCash(
                key = it.appPackage + notification + ApplicationNotificationsMarksCash.RECEIVED, appPackage = it.appPackage, time = notification, type = ApplicationNotificationsMarksCash.RECEIVED
            )
        } + it.notificationsSeen.map { notification -> //we combine received notifications with seen notifications with just a different types
            ApplicationNotificationsMarksCash(
                key = it.appPackage + notification + ApplicationNotificationsMarksCash.SEEN, appPackage = it.appPackage, time = notification, type = ApplicationNotificationsMarksCash.SEEN
            )
        },
    )
}
