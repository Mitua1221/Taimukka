package com.arjental.taimukka.presentaion.ui.screens.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.arjental.taimukka.domain.uc.CollectUserStatsUC
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.resource.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val collectUserStatsUC: CollectUserStatsUC
): TViewModel<MainState>() {

    private val state: MutableStateFlow<MainState> = MutableStateFlow(MainState.PageLoading())

    override fun collect(): StateFlow<MainState> = state.asStateFlow()




}