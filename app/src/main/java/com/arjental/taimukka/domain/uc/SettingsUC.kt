package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.data.cash.holders.SettingsHolder
import com.arjental.taimukka.data.settings.ColorScheme
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO("remove this class, different settings by different uc")
class SettingsUC @Inject constructor(
    private val settings: SettingsHolder,
) {
    suspend fun isSystemThemeUsed() = settings.isSystemThemeUsed()
    suspend fun setUseSystemTheme(useSystem: Boolean) = settings.setUseSystemTheme(useSystem = useSystem)
    suspend fun isDarkThemeEnabled() = settings.isDarkThemeEnabled()
    suspend fun setDarkTheme(enabled: Boolean) = settings.setDarkTheme(enabled = enabled)
    suspend fun getColorScheme(): Flow<ColorScheme> = settings.getColorScheme()
    suspend fun getTimeline(): Flow<Timeline> = settings.getTimelineSelection()
    suspend fun setTimeline(timeline: Timeline) = settings.setTimelineSelection(timeline)

}