package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import android.content.Context
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.domain.uc.CategorySelectionUC
import com.arjental.taimukka.domain.uc.SelectionTypeUC
import com.arjental.taimukka.domain.uc.TimelineUC
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.presentaion.applist.AppListItemPres
import com.arjental.taimukka.entities.presentaion.applist.CategoriesSelection
import com.arjental.taimukka.entities.presentaion.applist.filterWithCategory
import com.arjental.taimukka.entities.presentaion.applist.toPresentation
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.flow.handleErrors
import com.arjental.taimukka.other.utils.resource.onDataTransform
import com.arjental.taimukka.presentaion.ui.components.navigations.wNav
import com.arjental.taimukka.presentaion.ui.components.uiutils.createAddError
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts.ApplicationDetailsPart
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Provider

class AppListVM @Inject constructor(
    private val context: Context,
    private val dispatchers: TDispatcher,
    applicationsStatsUC: Provider<ApplicationsStatsUC>,
    timelineUC: Provider<TimelineUC>,
    categorySelectionUC: Provider<CategorySelectionUC>,
    selectionTypeUC: Provider<SelectionTypeUC>,
) : TViewModel<AppListState, AppListEffect>(
    initialState = AppListState(),
    dispatchers = dispatchers,
) {
    private val timelineUC by lazy { timelineUC.get()!! }
    private val categorySelectionUC by lazy { categorySelectionUC.get()!! }
    private val selectionTypeUC by lazy { selectionTypeUC.get()!! }
    private val applicationsStatsUC by lazy { applicationsStatsUC.get()!! }

    /** Map stores all applications from cache with current timeline to fast search */
    private val applicationsMap = mutableMapOf<String, LaunchedAppDomain>()

    private val _appListState = MutableStateFlow(ApplicationsListState()).apply {
        loadApplicationStats()
    }

    /** Object of application that currently selected on screen */
    private val _openedAppState = MutableStateFlow<AppListItemPres?>(null)

    /** Locker of [preselectFirstApplication] */
    private val appFirstTimeSelection = AtomicBoolean(false)

    private val _timelineState = MutableStateFlow<Timeline?>(null)

    private val _selectedCategoryState = MutableStateFlow<CategoriesSelection?>(null)

    private val _selectedType = MutableStateFlow<SelectionType?>(null)

    init {
        addApplicationDetailsToStack()
    }

    /**
     * Creates right element [ApplicationDetailsPart] with details of application, provide communication object
     * Deletes [LoadingPart] from right stack
     */
    private fun addApplicationDetailsToStack() {
        launch {
            val flow = _openedAppState.map { openedApp -> openedApp?.packageName }.stateIn(this@launch)
            modifyState {
                it.copy(
                    right = (listOf(
                        ApplicationDetailsPart(
                            openedApplication = flow
                        )
                    )).toImmutableList()
                )
            }
        }
    }

    fun appListState() = _appListState.asStateFlow()

    fun timeline() = _timelineState.asStateFlow()

    fun selectedCategory() = _selectedCategoryState.asStateFlow()

    fun selectedType() = _selectedType.asStateFlow()

    @OptIn(FlowPreview::class)
    private fun loadApplicationStats() {
        launch {
            var applicationsStatsJob: Job? = null
            timelineUC.getTimeline().collectLatest { timeline ->
                _timelineState.emit(timeline)
                applicationsStatsJob?.cancelAndJoin()
                applicationsStatsJob = this@AppListVM.launch {
                    selectionTypeUC.getTypeSelection().collectLatest { selectionType -> // collect selected type
                        _selectedType.emit(selectionType)
                        applicationsStatsUC.applicationsStats(timeline = timeline, selectionType = selectionType).handleErrors(defaultOnError = emptyList()).collectLatest { res -> //get values from manager
                            categorySelectionUC.getCategorySelection().collectLatest { categoryType -> //also get category type
                                res.onDataTransform(
                                    onLoading = { res -> _appListState.update { it.copy(list = res.data ?: persistentListOf(), loading = true, error = null) } },
                                    onSuccess = { res -> _appListState.update { it.copy(list = res.data, loading = false, error = null) } },
                                    onError = { res -> _appListState.update { it.copy(list = res.data ?: persistentListOf(), loading = false, error = createAddError(res.cause)) } },
                                    onDataTransform = {
                                        //save to map cache all applications
                                        saveApplications(it ?: emptyList())

                                        _selectedCategoryState.emit(
                                            CategoriesSelection(
                                                selectedCategory = categoryType,
                                                categoriesList = it?.mapNotNull { it.appCategory }?.distinct()?.toImmutableList() ?: persistentListOf()
                                            )
                                        )
                                        val presentationList = (it?.toPresentation(context) ?: persistentListOf()).filterWithCategory(categoryType)

                                        preselectFirstApplication(presentationList ?: emptyList())

                                        presentationList
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Selects first element from list, if flag [appFirstTimeSelection] is false
     */
    private suspend fun preselectFirstApplication(presentationList: List<AppListItemPres>) = coroutineScope {
        this.launch {
            if (presentationList.isNotEmpty() && appFirstTimeSelection.compareAndSet(false, true)) {
                modifyAppDetailsState(selectedApplication = presentationList.first(), onElevated = false)
            }
        }
    }

    /**
     * Save all applications to local [applicationsMap]
     */

    private suspend fun saveApplications(launchedAppDomains: List<LaunchedAppDomain>) = coroutineScope {
        this.launch {
            applicationsMap.clear()
            applicationsMap.putAll(launchedAppDomains.associateBy { it.appPackage })
        }
    }

    /**
     * Get application selected from UI
     * This method provides by custom selection on application item, so when modify state screen onElevated.
     */
    fun selectApplication(application: AppListItemPres) {
        launch {
            modifyAppDetailsState(selectedApplication = application, onElevated = true)
        }
    }

    /**
     * Used to modify [_appDetailsState] from all places.
     * @param selectedApplication
     * @param onElevated if user selected element by himself
     */
    private suspend fun modifyAppDetailsState(selectedApplication: AppListItemPres, onElevated: Boolean) = coroutineScope {
        //change state of opened application
        launch {
            _openedAppState.emit(selectedApplication)
        }
        //only if we need elevate it, we shows that
        if (onElevated)
            launch {
                stateValue().right.firstOrNull { it is ApplicationDetailsPart }?.let { changeScreen -> //protected bu null, but in real life it cant be null
                    wNav(vm = this@AppListVM, changeScreen = changeScreen)
                }
            }
    }

    fun changeTimeline(timeline: Timeline) {
        launch {
            timelineUC.changeTimeline(timeline)
        }
    }

    fun selectCategory(category: Int?) {
        launch {
            categorySelectionUC.setCategorySelection(category)
        }
    }

    fun changeSelectionType(selectionType: SelectionType) {
        launch {
            selectionTypeUC.setTypeSelection(selectionType)
        }
    }

}
