package com.arjental.taimukka.presentaion.ui.screens.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.domain.uc.CollectUserStatsUC
import com.arjental.taimukka.other.utils.components.TViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val collectUserStatsUC: CollectUserStatsUC
): TViewModel() {

    init {
        println("init")
    }

    fun launch() {
        viewModelScope.launch { collectUserStatsUC.collect() }
    }

}