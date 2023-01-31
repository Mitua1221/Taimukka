package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.data.cash.holders.AppLaunchedHolder
import javax.inject.Inject

class FirstLaunchUC @Inject constructor(
    private val appLaunchedHolder: AppLaunchedHolder,
) {

    suspend fun isFirstLaunch(): Boolean = appLaunchedHolder.isLaunched()

    suspend fun launchedFirstTime() = appLaunchedHolder.isLaunched()

}