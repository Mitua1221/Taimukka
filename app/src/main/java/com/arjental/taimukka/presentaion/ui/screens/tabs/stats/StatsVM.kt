package com.arjental.taimukka.presentaion.ui.screens.tabs.stats

import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.flow.handleErrors
import com.arjental.taimukka.other.utils.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatsVM @Inject constructor(
    private val applicationsStatsUC: ApplicationsStatsUC,
    private val dispatchers: TDispatcher,
) : TViewModel<StatsState, StatsEffect>(
    initialState = StatsState.PagePreparing(), dispatchers = dispatchers,
) {

    private var loadApplicationStatsNotRun = false
    private var loadApplicationStatsJob: Job? = null

    fun loadApplicationStats() {
        if (!loadApplicationStatsNotRun) {
            loadApplicationStatsNotRun = true
            loadApplicationStatsJob?.cancel()
            loadApplicationStatsJob = viewModelScope.launch(Dispatchers.Default) {
                applicationsStatsUC.collect().handleErrors(defaultOnError = emptyList()).collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            modifyState(StatsState.PageLoading())
                        }
                        is Resource.Error -> {
                            modifyState(StatsState.PageError())
                        }
                        is Resource.Success -> {
                            modifyState(StatsState.PageLoaded(it.data.map { it.appPackage }))
                        }
                    }
                }
            }
        }
    }


}