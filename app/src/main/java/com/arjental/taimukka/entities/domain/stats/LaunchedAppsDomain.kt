package com.arjental.taimukka.entities.domain.stats

import com.arjental.taimukka.entities.data.cash.ApplicationInfoCash
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash
import com.arjental.taimukka.entities.data.cash.ApplicationForegroundMarksCash
import com.arjental.taimukka.entities.data.cash.ApplicationNotificationsMarksCash
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.other.utils.annotataions.Category

/**
 * @param percentage means percents in float like 0.50 - means 50 percents
 * @param realQuality Quality to preview by different [SelectionType], may be in millis for [SelectionType.SCREEN_TIME],
 * in size for [SelectionType.SEANCES] or [SelectionType.NOTIFICATIONS].
 */
data class LaunchedAppDomain(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    @Category val appCategory: Int?,
    val launches: List<LaunchedAppTimeMarkDomain>,
    val notificationsMarks: List<NotificationsReceivedDomain>,
    val percentage: Float = 0f,
    val realQuality: Long = 0,
    val selectionType: SelectionType = SelectionType.SCREEN_TIME
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
                key = it.appPackage+launch.from,
                from = launch.from,
                to = launch.to,
                appPackage = it.appPackage
            )
        },
        notificationsMarks = it.notificationsMarks.map { notification ->
            ApplicationNotificationsMarksCash(
                key = it.appPackage+notification.time,
                appPackage = it.appPackage,
                time = notification.time
            )
        }
    )
}
