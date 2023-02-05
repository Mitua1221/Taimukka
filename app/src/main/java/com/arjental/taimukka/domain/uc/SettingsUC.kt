package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.data.cash.holders.SettingsHolder
import javax.inject.Inject

class SettingsUC @Inject constructor(
    private val settings: SettingsHolder,
) {
    suspend fun isSystemThemeUsed() = settings.isSystemThemeUsed()
    suspend fun setUseSystemTheme(useSystem: Boolean) = settings.setUseSystemTheme(useSystem = useSystem)
    suspend fun isDarkThemeEnabled() = settings.isDarkThemeEnabled()
    suspend fun setDarkTheme(enabled: Boolean) = settings.setDarkTheme(enabled = enabled)
}