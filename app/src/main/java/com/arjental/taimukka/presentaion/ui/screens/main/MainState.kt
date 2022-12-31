package com.arjental.taimukka.presentaion.ui.screens.main

sealed interface MainState {
    class PagePreparing(): MainState
    class PageLoading(): MainState
    class PageLoaded(
        val list: List<String>
    ): MainState
    class PageError(): MainState
}

