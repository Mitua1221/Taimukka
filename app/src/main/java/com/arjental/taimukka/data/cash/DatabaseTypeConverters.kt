package com.arjental.taimukka.data.cash

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DatabaseTypeConverters() {

    @TypeConverter
    fun toStringSet(set: Set<String>?): String = Json.encodeToString(set)

    @TypeConverter
    fun fromStringSet(stringSet: String): Set<String>? = Json.decodeFromString(stringSet)

}