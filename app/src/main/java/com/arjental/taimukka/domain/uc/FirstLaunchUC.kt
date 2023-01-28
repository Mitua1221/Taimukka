package com.arjental.taimukka.domain.uc

import android.content.Context
import kotlinx.coroutines.delay
import javax.inject.Inject

class FirstLaunchUC @Inject constructor(
    private val context: Context
) {

    suspend fun launchFirst(): Boolean {
        delay(2000)
        return true
    }


}