package com.arjental.taimukka.domain.uc

import com.arjental.taimukka.data.cash.holders.SettingsHolder
import com.arjental.taimukka.entities.pierce.selection_type.Type
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * For getting selected current [Type] on any screen
 */
class TypeUC @Inject constructor(
    private val settings: SettingsHolder,
) {
    fun getType(): Flow<Type> = settings.getType()
    suspend fun setType(type: Type) = settings.setType(type)
}