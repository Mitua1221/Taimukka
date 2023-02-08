package com.arjental.taimukka.presentaion.ui.screens.tabs.settings

import com.arjental.taimukka.domain.uc.SettingsUC
import com.arjental.taimukka.other.utils.components.TViewModel
import com.arjental.taimukka.other.utils.dispatchers.TDispatcher
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SettingsVM @Inject constructor(
    private val dispatchers: TDispatcher,
    private val settingsUC: SettingsUC,
) : TViewModel<SettingsState, SettingsEffect>(
    initialState = SettingsState(), dispatchers = dispatchers,
) {

    private val _settingsListState = MutableStateFlow(SettingsListElements()).apply {
        launch {
            val isDarkThemeEnabled = async { settingsUC.isDarkThemeEnabled() }
            val isSystemThemeUsed = async { settingsUC.isSystemThemeUsed() }
            delay(3000)
            update {
                it.createList(
                    useDefaultThemeSwitchState = isSystemThemeUsed.await(),
                    forceDarkModeSwitchState = isDarkThemeEnabled.await(),
                    forceDarkModeSwitchEnabled = !isSystemThemeUsed.await()
                )
            }

        }
    }

    init {
        println("SettingsVMSettingsVM init")
    }

    override fun onCleared() {
        super.onCleared()
        println("SettingsVMSettingsVM cleared")

    }

    fun settingsListState() = _settingsListState.asStateFlow()


    fun clickOnSettingsItem(type: SettingsType) {

    }

    fun onSwitchChanged(type: SettingsType, state: Boolean) {
        launch {
            when (type) {
                SettingsType.THEME -> {
                    launch { settingsUC.setDarkTheme(enabled = state) }
                    launch {
                        updateSettingsList {
                            if (it.type == type) {
                                it.copy(switchState = state)
                            } else it
                        }
                    }
                }
                SettingsType.THEME_DEFAULT -> {
                    launch { settingsUC.setUseSystemTheme(useSystem = state) }
                    launch {
                        updateSettingsList {
                            when {
                                it.type == type -> it.copy(switchState = state)
                                it.type == SettingsType.THEME -> {
                                    if (state) {
                                        it.copy(switchEnabled = false)
                                    } else {
                                        it.copy(switchEnabled = true)
                                    }
                                }
                                else -> it
                            }
                        }
                    }



                }
                else -> Unit
            }
        }
    }

    private suspend inline fun updateSettingsList(u: (SettingsListElement) -> SettingsListElement) {
        _settingsListState.update {
            it.copy(
                list = it.list.map { settingsItem ->
                    u(settingsItem)
                }.toImmutableList()
            )
        }
    }


}

