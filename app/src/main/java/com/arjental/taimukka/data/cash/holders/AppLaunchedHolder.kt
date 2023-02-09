package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.entities.data.cash.AppLaunched
import javax.inject.Inject

interface AppLaunchedHolder {
    suspend fun isLaunched(): Boolean
    suspend fun setLaunched()
}

class AppLaunchedHolderImpl @Inject constructor(
    private val database: Database
) : AppLaunchedHolder {

    private val LAUNCHED_KEY = "LAUNCHED_KEY"

    private val appLaunched = database.appLaunched()

    override suspend fun isLaunched(): Boolean =
        appLaunched.getLaunched(launchedKey = LAUNCHED_KEY)?.launched ?: false

    override suspend fun setLaunched() = appLaunched.setLaunched(
        idLaunched = AppLaunched(launchedKey = LAUNCHED_KEY, launched = true)
    )

}