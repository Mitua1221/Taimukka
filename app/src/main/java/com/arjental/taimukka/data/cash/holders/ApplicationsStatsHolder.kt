package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface ApplicationsStatsHolder {
    /**
     * Return all table
     */
    suspend fun getApplications(): List<ApplicationStatsCash>

    /**
     * Return only selected interval
     */
    suspend fun getApplications(from: Long, to: Long): List<ApplicationStatsCash>

    /**
     * Return only selected application in selected interval
     */
    suspend fun getApplication(from: Long, to: Long, appPackage: String): ApplicationStatsCash?
    suspend fun setApplications(applicationsList: List<ApplicationStatsCash>)
    suspend fun clearApplicationsList()
}

class ApplicationsStatsHolderImpl @Inject constructor(
    private val database: Database
) : ApplicationsStatsHolder {

    private val applicationsStats = database.applicationsStats()

    override suspend fun getApplications() = applicationsStats.getApplications()
    override suspend fun getApplications(from: Long, to: Long): List<ApplicationStatsCash> =
        applicationsStats.getApplications().map { appStatsCache ->
            appStatsCache.filterCashedAppFields(from = from, to = to)
        }

    override suspend fun getApplication(from: Long, to: Long, appPackage: String): ApplicationStatsCash? =
        applicationsStats.getApplication(appPackage = appPackage)?.filterCashedAppFields(from = from, to = to)

    override suspend fun setApplications(applicationsList: List<ApplicationStatsCash>): Unit = coroutineScope {
        applicationsList.map {
            async {
                applicationsStats.setApplication(app = it.appInfo)
                applicationsStats.setApplicationForegroundMarks(timeMarksList = it.foregroundMarks)
                applicationsStats.setApplicationNotificationsMarks(notificationMarks = it.notifications)
            }
        }.awaitAll()
    }

    override suspend fun clearApplicationsList() = applicationsStats.clear()


    /** Uses only standart filter for all fuctions.
     *  Helps to filter all fields from---to
     */
    private fun ApplicationStatsCash.filterCashedAppFields(from: Long, to: Long) = this.copy(
        //here we filtering result to match given from-to
        foregroundMarks = this.foregroundMarks.filter {
            it.from >= from && it.to <= to
        },
        //here we filtering result to match given from-to
        notifications = this.notifications.filter { it.time in from..to }
    )

}