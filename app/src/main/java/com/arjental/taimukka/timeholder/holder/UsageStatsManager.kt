package com.arjental.taimukka.timeholder.holder

import javax.inject.Inject

interface UsageStatsManager {

    suspend fun launch()

}

class UsageStatsManagerImpl @Inject constructor(): UsageStatsManager{

    override suspend fun launch() {
        TODO("Not yet implemented")
    }

}