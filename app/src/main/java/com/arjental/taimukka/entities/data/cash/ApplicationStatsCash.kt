package com.arjental.taimukka.entities.data.cash

import androidx.room.*
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.domain.stats.LaunchedAppTimeMarkDomain
import java.util.*

data class ApplicationStatsCash(
    @Embedded val appInfo: ApplicationInfoCash,
    @Relation(
        parentColumn = "app_package",
        entityColumn = "app_package_sync"
    )
    val timeMarks: List<ApplicationTimeMarksCash>
)

@Entity(tableName = "applications_info")
data class ApplicationInfoCash(
    @ColumnInfo(name = "app_package") @PrimaryKey val appPackage: String,
    @ColumnInfo(name = "app_name") val appName: String,
    @ColumnInfo(name = "app_non_system") val nonSystem: Boolean,
)

@Entity(tableName = "applications_time_marks")
data class ApplicationTimeMarksCash(
    @ColumnInfo(name = "time_key") @PrimaryKey(autoGenerate = true) val key: Long = 0,
    @ColumnInfo(name = "app_package_sync") val appPackage: String,
    @ColumnInfo(name = "from") val from: Long,
    @ColumnInfo(name = "to") val to: Long,
)

suspend fun List<ApplicationStatsCash>.toDomain() = this.map {
    LaunchedAppDomain(
        appPackage = it.appInfo.appPackage,
        appName = it.appInfo.appName,
        nonSystem = it.appInfo.nonSystem,
        launches = it.timeMarks.map { timeMark ->
            LaunchedAppTimeMarkDomain(
                from = timeMark.from,
                to = timeMark.to
            )
        }
    )
}