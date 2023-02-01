package com.arjental.taimukka.presentaion.ui.screens.tabs.settings

import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import javax.inject.Inject

class SettingsVM @Inject constructor(
    private val dispatchers: TDispatcher,
) : TViewModel<SettingsState, SettingsEffect>(
    initialState = SettingsState(), dispatchers = dispatchers,
) {

}