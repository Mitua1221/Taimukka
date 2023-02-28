package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.data.cash.holders.SettingsHolder
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * For getting selected timeline all any screen
 */
class CategorySelectionUC @Inject constructor(
    private val settings: SettingsHolder,
) {
    fun getCategorySelection(): Flow<Int?> = settings.getCategorySelection()
    suspend fun setCategorySelection(category: Int?) = settings.setCategorySelection(category)
}