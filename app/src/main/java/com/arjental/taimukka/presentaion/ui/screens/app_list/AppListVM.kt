package com.arjental.taimukka.presentaion.ui.screens.app_list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.entities.presentaion.applist.toPresentation
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

class AppListVM @Inject constructor(
    private val applicationsStatsUC: ApplicationsStatsUC,
    private val context: Context
) : TViewModel<AppListState, AppListEffect>() {

    private val state: MutableStateFlow<AppListState> = MutableStateFlow(AppListState.PagePreparing())

    override fun collectState(): StateFlow<AppListState> = state.asStateFlow()

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
                            state.value =  AppListState.PageLoading(emptyList())
                        }
                        is Resource.Error -> {
                            state.value =  AppListState.PageError()
                        }
                        is Resource.Success -> {
                            state.value =  AppListState.PageLoaded( it.data.toPresentation(context))
                        }
                    }
                }
            }
        }
    }





}