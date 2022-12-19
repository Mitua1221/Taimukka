package com.arjental.taimukka.domain.uc

import android.content.Context
import com.arjental.taimukka.domain.repos.UsageStatsManager
import javax.inject.Inject

class CollectUserStatsUC @Inject constructor(
    private val usageStatsManager: UsageStatsManager
) {

    suspend fun collect() = usageStatsManager.launch()

}