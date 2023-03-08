package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash
import kotlinx.coroutines.async
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
    suspend fun setApplications(applicationsList: List<ApplicationStatsCash>)
    suspend fun clearApplicationsList()
}

class ApplicationsStatsHolderImpl @Inject constructor(
    private val database: Database
) : ApplicationsStatsHolder {

    private val applicationsStats = database.applicationsStats()

    override suspend fun getApplications() = applicationsStats.getApplications()
    override suspend fun getApplications(from: Long, to: Long): List<ApplicationStatsCash> = applicationsStats.getApplications().map { appStatsCache ->
        appStatsCache.copy(
            //here we filtering result to match given from-to
            foregroundMarks = appStatsCache.foregroundMarks.filter {
                it.from >= from && it.to <= to
            },
            //here we filtering result to match given from-to
            notificationsMarks = appStatsCache.notificationsMarks.filter { it.time in from..to })
    }

    override suspend fun setApplications(applicationsList: List<ApplicationStatsCash>) = coroutineScope {
        applicationsList.map {
            async {
                applicationsStats.setApplication(app = it.appInfo)
                applicationsStats.setApplicationForegroundMarks(timeMarksList = it.foregroundMarks)
                applicationsStats.setApplicationNotificationsMarks(notificationMarks = it.notificationsMarks)
            }
        }.forEach { it.await() }
    }

    override suspend fun clearApplicationsList() = applicationsStats.clear()

}