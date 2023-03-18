package com.arjental.taimukka.entities.data.user_stats

import com.arjental.taimukka.other.utils.annotataions.Category

data class AppInformation(
    val name: String = "-",
    val packageName: String,
    val isAppSystem: Boolean,
    @Category val appCategory: Int?,
)
