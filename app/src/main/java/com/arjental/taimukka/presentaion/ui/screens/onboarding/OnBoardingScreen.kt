package com.arjental.taimukka.presentaion.ui.screens.onboarding

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class OnBoardingScreen(val onBoardingList: List<OnBoardingScreenTypes>) : Screen {

    @Composable
    override fun Content() {
        OnBoarding(
            onBoardingList = onBoardingList
        )
    }

}