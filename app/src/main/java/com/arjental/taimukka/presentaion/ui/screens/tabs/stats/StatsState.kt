package com.arjental.taimukka.presentaion.ui.screens.tabs.stats

sealed interface StatsState {
    class PagePreparing(): StatsState
    class PageLoading(): StatsState
    class PageLoaded(
        val list: List<String>
    ): StatsState
    class PageError(): StatsState
}

