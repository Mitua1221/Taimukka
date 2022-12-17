package com.arjental.taimukka.ui.screens.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.uc.CollectUserStatsUC
import com.arjental.taimukka.utils.components.TViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val collectUserStatsUC: CollectUserStatsUC
): TViewModel() {

    fun launch(context: Context) {
        viewModelScope.launch { collectUserStatsUC.collect(context = context) }
    }

}