package com.arjental.taimukka.presentaion.ui.screens.splash

import com.arjental.taimukka.domain.uc.CheckPermissionsUC
import com.arjental.taimukka.domain.uc.FirstLaunchUC
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import kotlinx.coroutines.*
import javax.inject.Inject

class SplashVM @Inject constructor(
    private val checkPermissions: CheckPermissionsUC,
    private val firstLaunchUC: FirstLaunchUC,
    private val dispatchers: TDispatcher,
) : TViewModel<SplashState, SplashEffect>(initialState = SplashState.Loading(), dispatchers = dispatchers) {

//    private val _needToRequestPermissions: MutableStateFlow<Boolean?> = MutableStateFlow(null)
//    val needToRequestPermissions = _needToRequestPermissions.asStateFlow()
//
//    private val _mapOfPermissionsToRequest = MutableStateFlow(emptyMap<TPermission, Boolean>())
//    val mapOfPermissionsToRequest = _mapOfPermissionsToRequest.asStateFlow()

    init {
        launch()
    }

    private fun launch() {
        launch {
            val permissionsToRequest = async { checkPermissions.checkSplashPermissions() }
            val launchFirst = async { firstLaunchUC.launchFirst() }
            modifyState(
                SplashState.State(
                    firstLaunch = launchFirst.await(),
                    needRequestPermissions = permissionsToRequest.await().isNotEmpty()
                )
            )
        }
    }

    fun ensureSplashActive(): Boolean = stateValue() is SplashState.Loading

}