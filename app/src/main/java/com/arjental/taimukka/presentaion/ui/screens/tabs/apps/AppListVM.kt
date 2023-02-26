package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import android.content.Context
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.domain.uc.SettingsUC
import com.arjental.taimukka.domain.uc.TimelineUC
import com.arjental.taimukka.entities.pierce.FLOW_MERGE_DEBOUNCE
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.pierce.timeline.TimelineType
import com.arjental.taimukka.entities.presentaion.applist.toPresentation
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.flow.handleErrors
import com.arjental.taimukka.other.utils.resource.Resource
import com.arjental.taimukka.presentaion.ui.components.uiutils.createAddError
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AppListVM @Inject constructor(
    private val applicationsStatsUC: ApplicationsStatsUC,
    private val context: Context,
    private val dispatchers: TDispatcher,
    private val settings: TimelineUC,
) : TViewModel<AppListState, AppListEffect>(
    initialState = AppListState(),
    dispatchers = dispatchers,
) {

    private val _appListState = MutableStateFlow(ApplicationsListState()).apply {
        loadApplicationStats()
    }

    private val _timelineState = MutableStateFlow<Timeline?>(null)

    fun appListState() = _appListState.asStateFlow()

    fun timeline() = _timelineState.asStateFlow()

    @OptIn(FlowPreview::class)
    private fun loadApplicationStats() {
        launch {
            var applicationsStatsJob: Job? = null
            settings.getTimeline().debounce(FLOW_MERGE_DEBOUNCE).collectLatest { timeline ->
                _timelineState.emit(timeline)
                applicationsStatsJob?.cancelAndJoin()
                applicationsStatsJob = this@AppListVM.launch {
                    applicationsStatsUC.applicationsStats(timeline = timeline).handleErrors(defaultOnError = emptyList()).collectLatest { res ->
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
    }

    fun changeTimeline(timeline: Timeline) {
        launch {
            settings.changeTimeline(timeline)
        }
    }

}