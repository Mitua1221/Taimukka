package com.arjental.taimukka.presentaion.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.flow.handleErrors
import com.arjental.taimukka.other.utils.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val applicationsStatsUC: ApplicationsStatsUC
) : TViewModel<MainState, MainEffect>() {

    private val state: MutableStateFlow<MainState> = MutableStateFlow(MainState.PagePreparing())

    override fun collectState(): StateFlow<MainState> = state.asStateFlow()

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
                            state.value =  MainState.PageLoading()
                        }
                        is Resource.Error -> {
                            state.value =  MainState.PageError()
                        }
                        is Resource.Success -> {
                            state.value =  MainState.PageLoaded( it.data.map { it.appPackage })
                        }
                    }
                }
            }
        }
    }


}