package com.arjental.taimukka.presentaion.ui.screens.tabs.apps

import android.content.Context
import com.arjental.taimukka.domain.uc.ApplicationsStatsUC
import com.arjental.taimukka.domain.uc.CategorySelectionUC
import com.arjental.taimukka.domain.uc.TimelineUC
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.presentaion.applist.CategoriesSelection
import com.arjental.taimukka.entities.presentaion.applist.filterWithCategory
import com.arjental.taimukka.entities.presentaion.applist.toPresentation
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.other.utils.flow.handleErrors
import com.arjental.taimukka.other.utils.resource.onDataTransform
import com.arjental.taimukka.presentaion.ui.components.uiutils.createAddError
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class AppListVM @Inject constructor(
    private val applicationsStatsUC: ApplicationsStatsUC,
    private val context: Context,
    private val dispatchers: TDispatcher,
    private val timelineUC: TimelineUC,
    categorySelectionUC: Provider<CategorySelectionUC>
) : TViewModel<AppListState, AppListEffect>(
    initialState = AppListState(),
    dispatchers = dispatchers,
) {
    private val categorySelectionUC by lazy { categorySelectionUC.get()!! }

    private val _appListState = MutableStateFlow(ApplicationsListState()).apply {
        loadApplicationStats()
    }

    private val _timelineState = MutableStateFlow<Timeline?>(null)

    private val _selectedCategoryState = MutableStateFlow<CategoriesSelection?>(null)

    fun appListState() = _appListState.asStateFlow()

    fun timeline() = _timelineState.asStateFlow()

    fun selectedCategory() = _selectedCategoryState.asStateFlow()

    @OptIn(FlowPreview::class)
    private fun loadApplicationStats() {
        launch {
            var applicationsStatsJob: Job? = null
            timelineUC.getTimeline().collectLatest { timeline ->
                _timelineState.emit(timeline)
                applicationsStatsJob?.cancelAndJoin()
                applicationsStatsJob = this@AppListVM.launch {
                    applicationsStatsUC.applicationsStats(timeline = timeline).handleErrors(defaultOnError = emptyList()).collectLatest { res -> //get values from manager
                        categorySelectionUC.getCategorySelection().collectLatest { categoryType -> //also get category type
                            res.onDataTransform(
                                onLoading = { res -> _appListState.update { it.copy(list = res.data ?: persistentListOf(), loading = true, error = null) } },
                                onSuccess = { res -> _appListState.update { it.copy(list = res.data, loading = false, error = null) } },
                                onError = { res -> _appListState.update { it.copy(list = res.data ?: persistentListOf(), loading = false, error = createAddError(res.cause)) } },
                                onDataTransform = {
                                    //set filters that we can select from
                                    _selectedCategoryState.emit(
                                        CategoriesSelection(
                                            selectedCategory = categoryType,
                                            categoriesList = it?.mapNotNull { it.appCategory }?.distinct()?.toImmutableList() ?: persistentListOf()
                                        )
                                    )
                                    val presentationList = (it?.toPresentation(context) ?: persistentListOf()).filterWithCategory(categoryType)
                                    presentationList
                                }
                            )
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

    fun selectCategory(category: Int?) {
        launch {
            categorySelectionUC.setCategorySelection(category)
        }
    }

}
