package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import android.content.Context
import com.arjental.taimukka.domain.uc.ApplicationStatsUC
import com.arjental.taimukka.domain.uc.TypeUC
import com.arjental.taimukka.domain.uc.TimelineUC
import com.arjental.taimukka.entities.pierce.selection_type.Type
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.presentaion.app_details.toPresentation
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.resource.onDataTransform
import com.arjental.taimukka.presentaion.ui.components.uiutils.createAddError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider

class AppDetailsVM @Inject constructor(
    private val context: Context,
    private val dispatchers: TDispatcher,
    _timelineUC: Provider<TimelineUC>,
    _typeUC: Provider<TypeUC>,
    applicationStatsUC: Provider<ApplicationStatsUC>,
) : TViewModel<ApplicationDetailsState, Unit>(
    initialState = ApplicationDetailsState(),
    dispatchers = dispatchers,
) {
    private val timelineUC by lazy { _timelineUC.get()!! }
    private val typeUC by lazy { _typeUC.get()!! }
    private val applicationStatsUC by lazy { applicationStatsUC.get()!! }

    private var setApplicationJob: Job? = null

    private val _timelineState = MutableStateFlow<Timeline?>(null)

    private val _type = MutableStateFlow<Type?>(null)

    private var applicationPackage: String = ""

    fun timeline() = _timelineState.asStateFlow()

    fun selectedType() = _type.asStateFlow()

    /**
     * Receive application package from other screen
     */
    fun setApplication(applicationPackage: String) {
        if (applicationPackage != this.applicationPackage) {
            this.applicationPackage = applicationPackage
            setApplicationJob?.cancel()
            setApplicationJob = launch {
                modifyState {
                    ApplicationDetailsState() //clear to default when app reselected
                }
                timelineUC.getTimeline().collectLatest { timeline ->
                    _timelineState.emit(timeline)
                    typeUC.getType().collectLatest { type ->
                        _type.emit(type)
                        timeline.let {
                            applicationStatsUC.applicationStats(timeline = timeline, appPackage = applicationPackage).collectLatest {
                                launch {
                                    it.onDataTransform(
                                        onLoading = { res -> modifyState { it.copy(loading = true) } },
                                        onSuccess = { res -> modifyState { it.copy(appPresentationInformation = res.data, loading = false, error = null) } },
                                        onError = { res -> modifyState { it.copy(appPresentationInformation = res.data, loading = false, error = createAddError(res.cause)) } },
                                        onDataTransform = { it?.toPresentation(context) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun changeTimeline(timeline: Timeline) {
        launch {
            timelineUC.changeTimeline(timeline)
        }
    }

    fun changeType(type: Type) {
        launch {
            typeUC.setType(type)
        }
    }

}