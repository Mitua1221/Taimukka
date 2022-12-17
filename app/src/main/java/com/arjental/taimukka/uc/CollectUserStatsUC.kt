package com.arjental.taimukka.uc

import android.content.Context
import com.arjental.taimukka.repos.user_stats_manager.UsageStatsManager
import javax.inject.Inject

class CollectUserStatsUC @Inject constructor(
    private val usageStatsManager: UsageStatsManager
) {

    suspend fun collect(context: Context) = usageStatsManager.launch(context = context)

}