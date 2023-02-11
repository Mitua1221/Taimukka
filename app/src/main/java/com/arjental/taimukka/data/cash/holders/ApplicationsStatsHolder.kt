package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.entities.data.cash.ApplicationStatsCash
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface ApplicationsStatsHolder {
    suspend fun getApplications(): List<ApplicationStatsCash>
    suspend fun setApplications(applicationsList: List<ApplicationStatsCash>)
}

class ApplicationsStatsHolderImpl @Inject constructor(
    private val database: Database
) : ApplicationsStatsHolder {

    private val applicationsStats = database.applicationsStats()

    override suspend fun getApplications() = applicationsStats.getApplications()

    override suspend fun setApplications(applicationsList: List<ApplicationStatsCash>) = coroutineScope {
        applicationsList.map {
            async {
                applicationsStats.setApplication(app = it.appInfo)
                applicationsStats.setApplicationTimeMarks(timeMarksList = it.timeMarks)
            }
        }.forEach { it.await() }
    }

}