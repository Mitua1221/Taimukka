package com.arjental.taimukka.presentaion.ui.screens.splash

import com.arjental.taimukka.presentaion.ui.screens.onboarding.OnBoardingScreenTypes

sealed interface SplashState {

    class Loading() : SplashState
    class State(
        val showOnBoarding: Boolean,
        val onBoardingScreens: List<OnBoardingScreenTypes> = emptyList()
    ) : SplashState

}