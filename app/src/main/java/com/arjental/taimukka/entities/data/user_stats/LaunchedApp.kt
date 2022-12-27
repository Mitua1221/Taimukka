package com.arjental.taimukka.entities.data.user_stats

class LaunchedApp(
    val appPackage: String,
    val appName: String,
    val nonSystem: Boolean,
    val launches: MutableList<Pair<Long, Long>>,
)