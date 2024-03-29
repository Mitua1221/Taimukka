package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.data.settings.ColorScheme
import com.arjental.taimukka.entities.data.cash.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingsHolder {
    suspend fun isSystemThemeUsed(): Boolean
    suspend fun setUseSystemTheme(useSystem: Boolean)
    suspend fun isDarkThemeEnabled(): Boolean
    suspend fun setDarkTheme(enabled: Boolean)
    fun getColorScheme(): Flow<ColorScheme>
}

class SettingsHolderImpl @Inject constructor(
    private val database: Database
) : SettingsHolder {

    private val USE_SYSTEM_THEME = "USE_SYSTEM_THEME"
    private val USE_DARK_THEME = "USE_DARK_THEME"

    private val TRUE = "TRUE"
    private val FALSE = "FALSE"

    private val settings by lazy { database.settings() }

    private fun ki(b: Boolean) = if (b) TRUE else FALSE
    private fun wrap(k: String, v: String) = AppSettings(settingsKey = k, settingsValue = v)

    override suspend fun isSystemThemeUsed(): Boolean {
        return settings.getSettingsItem(settingKey = USE_SYSTEM_THEME)?.settingsValue?.equals(TRUE) ?: true
    }

    override suspend fun setUseSystemTheme(useSystem: Boolean) {
        settings.setSettingsItem(wrap(k = USE_SYSTEM_THEME, v = ki(useSystem)))
    }

    override suspend fun isDarkThemeEnabled(): Boolean {
        return settings.getSettingsItem(settingKey = USE_DARK_THEME)?.settingsValue?.equals(TRUE) ?: false
    }

    override suspend fun setDarkTheme(enabled: Boolean) {
        settings.setSettingsItem(wrap(k = USE_DARK_THEME, v = ki(enabled)))
    }

    override fun getColorScheme(): Flow<ColorScheme> =
        settings.getSettingsItemFlow(settingKey = USE_SYSTEM_THEME).map { it?.settingsValue?.equals(TRUE) ?: true }.combine(
            settings.getSettingsItemFlow(settingKey = USE_DARK_THEME).map { it?.settingsValue?.equals(TRUE) ?: false }
        ) { systhemTheme, useDarkMode ->
            when {
                systhemTheme -> ColorScheme.SYS
                useDarkMode -> ColorScheme.NIGHT
                else -> ColorScheme.DAY
            }

        }


}


