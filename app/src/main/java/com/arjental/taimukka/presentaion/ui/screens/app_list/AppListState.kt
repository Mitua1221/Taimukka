package com.arjental.taimukka.presentaion.ui.screens.app_list

import com.arjental.taimukka.entities.presentaion.applist.AppListItemPres

sealed interface AppListState {
    class PagePreparing(): AppListState
    class PageLoading(
        val list: List<AppListItemPres>
    ): AppListState
    class PageLoaded(
        val list: List<AppListItemPres>
    ): AppListState
    class PageError(): AppListState
}