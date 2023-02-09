package com.arjental.taimukka.presentaion.ui.screens.tabs.app_list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.entities.presentaion.applist.toPresentation
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.flow.handleErrors
import com.arjental.taimukka.other.utils.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppListVM @Inject constructor(
    private val applicationsStatsUC: ApplicationsStatsUC,
    private val context: Context,
    private val dispatchers: TDispatcher,
) : TViewModel<AppListState, AppListEffect>(
    initialState = AppListState.PagePreparing(),
    dispatchers = dispatchers,
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
                            modifyState(AppListState.PageLoading(emptyList()))
                        }
                        is Resource.Error -> {
                            modifyState(AppListState.PageError())
                        }
                        is Resource.Success -> {
                            modifyState(AppListState.PageLoaded(it.data.toPresentation(context)))
                        }
                    }
                }
            }
        }
    }


}