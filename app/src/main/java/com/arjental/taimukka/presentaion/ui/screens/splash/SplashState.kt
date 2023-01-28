package com.arjental.taimukka.presentaion.ui.screens.splash

sealed interface SplashState {

    class Loading(): SplashState
    class State(
        val firstLaunch: Boolean = false,
        val needRequestPermissions: Boolean = false,
    ): SplashState

}