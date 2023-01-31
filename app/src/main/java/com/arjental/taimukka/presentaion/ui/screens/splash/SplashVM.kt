package com.arjental.taimukka.presentaion.ui.screens.splash

import com.arjental.taimukka.domain.uc.CheckPermissionsUC
import com.arjental.taimukka.domain.uc.FirstLaunchUC
import com.arjental.taimukka.domain.uc.TPermission
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import com.arjental.taimukka.presentaion.ui.screens.onboarding.OnBoardingScreenTypes
import kotlinx.coroutines.async
import javax.inject.Inject

class SplashVM @Inject constructor(
    private val checkPermissions: CheckPermissionsUC,
    private val firstLaunchUC: FirstLaunchUC,
    private val dispatchers: TDispatcher,
) : TViewModel<SplashState, SplashEffect>(initialState = SplashState.Loading(), dispatchers = dispatchers) {

    init {
        launch()
    }

    private fun launch() {
        launch {
            val permissionsToRequest = async {
                checkPermissions.checkSplashPermissions()
                    //map only permissions that not granted
                    .mapNotNull { if (it.value) it.key else null }
            }
            val launchFirst = async { firstLaunchUC.isFirstLaunch() }
            val showOnBoarding = launchFirst.await() || permissionsToRequest.await().isNotEmpty()
            val listOnBoardingScreens = mutableListOf<OnBoardingScreenTypes>()

            if (showOnBoarding) {
                if (launchFirst.await()) {
                    listOnBoardingScreens.add(OnBoardingScreenTypes.FirstLaunch())
                    listOnBoardingScreens.add(OnBoardingScreenTypes.ApplicationDescription())
                }
                listOnBoardingScreens.addAll(permissionsToRequest.await().map { permission ->
                    OnBoardingScreenTypes.PermissionsRequest(permission = permission)
                })
            }

            modifyState(
                SplashState.State(
                    showOnBoarding = showOnBoarding,
                    onBoardingScreens = listOnBoardingScreens
                )
            )
        }
    }

    fun lastPermissionGranted() {
        modifyState { SplashState.State(showOnBoarding = false) }
        launch { firstLaunchUC }
    }

    fun ensureSplashActive(): Boolean = stateValue() is SplashState.Loading

    suspend fun ensurePermissionGranted(permissionType: TPermission): Boolean = checkPermissions.checkPermission(permissionType)

}