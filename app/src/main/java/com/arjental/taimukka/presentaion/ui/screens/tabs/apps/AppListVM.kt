package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import android.content.Context
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.entities.presentaion.applist.toPresentation
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.flow.handleErrors
import com.arjental.taimukka.other.utils.resource.Resource
import com.arjental.taimukka.presentaion.ui.components.uiutils.createAddError
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AppListVM @Inject constructor(
    private val applicationsStatsUC: ApplicationsStatsUC,
    private val context: Context,
    private val dispatchers: TDispatcher,
) : TViewModel<AppListState, AppListEffect>(
    initialState = AppListState(),
    dispatchers = dispatchers,
) {

    private val _appListState = MutableStateFlow(ApplicationsListState()).apply {
        loadApplicationStats()
    }

    fun appListState() = _appListState.asStateFlow()

    private fun loadApplicationStats() {
        launch {
            applicationsStatsUC.collect().handleErrors(defaultOnError = emptyList()).collectLatest {  res ->
                when (res) {
                    is Resource.Loading -> {
                        _appListState.update { it.copy(list = res.data?.toPresentation(context) ?: persistentListOf(), loading = true, error = null) }
                    }
                    is Resource.Error -> {
                        _appListState.update { it.copy(list = res.data?.toPresentation(context) ?: persistentListOf(), loading = false, error = createAddError(res.cause)) }
                    }
                    is Resource.Success -> {
                        _appListState.update { it.copy(list = res.data.toPresentation(context), loading = false, error = null) }
                    }
                }
            }
        }
    }


}