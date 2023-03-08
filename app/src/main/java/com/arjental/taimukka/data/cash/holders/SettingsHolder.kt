package com.arjental.taimukka.data.cash.holders

import com.arjental.taimukka.data.cash.Database
import com.arjental.taimukka.data.settings.ColorScheme
import com.arjental.taimukka.entities.data.cash.AppSettings
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.pierce.timeline.TimelineType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SettingsHolder {
    suspend fun isSystemThemeUsed(): Boolean
    suspend fun setUseSystemTheme(useSystem: Boolean)
    suspend fun isDarkThemeEnabled(): Boolean
    suspend fun setDarkTheme(enabled: Boolean)
    fun getColorScheme(): Flow<ColorScheme>
    fun getTimelineSelection(): Flow<Timeline>

    /**
     * Set selection from, to
     */
    suspend fun setTimelineSelection(timeline: Timeline)

    /**
     * Category is always int
     * If it returns -1 or null category is not selected
     */
    fun getCategorySelection(): Flow<Int?>

    /**
     * Save category, if category is not selected because we clear it, we can save -1,
     * but not forget to support it in [getCategorySelection] method.
     */
    suspend fun setCategorySelection(category: Int?)

    /**
     * Get selected category that selected now, defined by [SelectionType].
     */
    fun getTypeSelection(): Flow<SelectionType>

    /**
     * Save selected type for all app, typed by [SelectionType]
     */
    suspend fun setTypeSelection(selectionType: SelectionType)
}

class SettingsHolderImpl @Inject constructor(
    private val database: Database
) : SettingsHolder {

    private val USE_SYSTEM_THEME = "USE_SYSTEM_THEME"
    private val USE_DARK_THEME = "USE_DARK_THEME"

    //timeline constants
    private val COMMON_TIMELINE_TYPE = "COMMON_TIMELINE_TYPE"
    private val COMMON_TIMELINE_FROM = "COMMON_TIMELINE_FROM"
    private val COMMON_TIMELINE_TO = "COMMON_TIMELINE_TO"

    //selected category for filter
    private val SELECTED_CATEGORY = "SELECTED_CATEGORY"

    //selected type for filter
    private val SELECTED_TYPE = "SELECTED_TYPE"

    private val TRUE = "TRUE"
    private val FALSE = "FALSE"

    private val settings by lazy { database.settings() }

    private fun ki(b: Boolean) = if (b) TRUE else FALSE

    /**
     * Helps to wrap creation of [AppSettings] object that stores info in settings db
     */
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

    override fun getTimelineSelection(): Flow<Timeline> {
        return settings.getSettingsItemFlow(settingKey = COMMON_TIMELINE_TYPE).map { getTimeline(it?.settingsValue) }.combine(
            settings.getSettingsItemFlow(settingKey = COMMON_TIMELINE_FROM).map { it?.settingsValue?.toLongOrNull() }
        ) { timelineType, from ->
            Timeline(timelineType = timelineType, from = from)
        }.combine(
            settings.getSettingsItemFlow(settingKey = COMMON_TIMELINE_TO).map { it?.settingsValue?.toLongOrNull() }
        ) { timeline, to ->
            timeline.copy(to = to)
        }
    }

    private fun getTimeline(type: String?): TimelineType {
        return try {
            TimelineType.valueOf(type ?: return TimelineType.WEEK)
        } catch (e: IllegalArgumentException) {
            return TimelineType.WEEK
        }
    }

    override suspend fun setTimelineSelection(timeline: Timeline) = coroutineScope {
        launch { settings.setSettingsItem(wrap(k = COMMON_TIMELINE_TYPE, v = timeline.timelineType.name)) }
        settings.setSettingsItem(wrap(k = COMMON_TIMELINE_FROM, v = timeline.from.toString()))
        settings.setSettingsItem(wrap(k = COMMON_TIMELINE_TO, v = timeline.to.toString()))
    }

    override fun getCategorySelection(): Flow<Int?> =
        settings.getSettingsItemFlow(settingKey = SELECTED_CATEGORY).map {
            val value = it?.settingsValue?.toInt()
            if (value == -1) null else value
        }

    override suspend fun setCategorySelection(category: Int?) {
        settings.setSettingsItem(wrap(k = SELECTED_CATEGORY, v = category?.toString() ?: (-1).toString()))
    }

    override fun getTypeSelection(): Flow<SelectionType> =
        settings.getSettingsItemFlow(settingKey = SELECTED_TYPE).map { selectedType ->
            selectedType?.settingsValue?.let { SelectionType.valueOf(it) } ?: SelectionType.SCREEN_TIME
        }

    override suspend fun setTypeSelection(selectionType: SelectionType) =
        settings.setSettingsItem(wrap(k = SELECTED_TYPE, v = selectionType.toString()))

}


