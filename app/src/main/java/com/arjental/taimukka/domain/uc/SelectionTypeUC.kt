package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.data.cash.holders.SettingsHolder
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * For getting selected current [SelectionType] on any screen
 */
class SelectionTypeUC @Inject constructor(
    private val settings: SettingsHolder,
) {
    fun getTypeSelection(): Flow<SelectionType> = settings.getTypeSelection()
    suspend fun setTypeSelection(selectionType: SelectionType) = settings.setTypeSelection(selectionType)
}