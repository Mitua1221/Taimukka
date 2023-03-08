package com.arjental.taimukka.entities.data.cash

import androidx.room.*
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppTimeMarkDomain
import com.arjental.taimukka.entities.domain.stats.NotificationsReceivedDomain
import com.arjental.taimukka.other.utils.annotataions.Category

data class ApplicationStatsCash(
    @Embedded val appInfo: ApplicationInfoCash,
    @Relation(
        parentColumn = "app_package",
        entityColumn = "app_package_sync"
    )
    val foregroundMarks: List<ApplicationForegroundMarksCash>,
    @Relation(
        parentColumn = "app_package",
        entityColumn = "app_package_sync"
    )
    val notificationsMarks: List<ApplicationNotificationsMarksCash>
)

@Entity(tableName = "applications_info")
class ApplicationInfoCash(
    @ColumnInfo(name = "app_package") @PrimaryKey val appPackage: String,
    @ColumnInfo(name = "app_name") val appName: String,
    @ColumnInfo(name = "app_non_system") val nonSystem: Boolean,
    @ColumnInfo(name = "app_category") @Category val appCategory: Int? = null,
)

@Entity(tableName = "applications_foreground_marks")
class ApplicationForegroundMarksCash(
    @ColumnInfo(name = "key_with_time") @PrimaryKey val key: String,
    @ColumnInfo(name = "app_package_sync") val appPackage: String,
    @ColumnInfo(name = "from") val from: Long,
    @ColumnInfo(name = "to") val to: Long,
)

@Entity(tableName = "applications_notifications_marks")
class ApplicationNotificationsMarksCash(
    @ColumnInfo(name = "key_with_time") @PrimaryKey val key: String,
    @ColumnInfo(name = "app_package_sync") val appPackage: String,
    @ColumnInfo(name = "time") val time: Long,
)

suspend fun List<ApplicationStatsCash>.toDomain() = this.map {
    LaunchedAppDomain(
        appPackage = it.appInfo.appPackage,
        appName = it.appInfo.appName,
        nonSystem = it.appInfo.nonSystem,
        launches = it.foregroundMarks.map { timeMark ->
            LaunchedAppTimeMarkDomain(
                from = timeMark.from,
                to = timeMark.to
            )
        },
        appCategory = it.appInfo.appCategory,
        notificationsMarks = it.notificationsMarks.map {
            NotificationsReceivedDomain(
                time = it.time
            )
        }
    )
}