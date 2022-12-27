package com.arjental.taimukka.entities.presentaion.userstats

data class MainPageUserStats(
    val intervalInMillis: Long,
    val listAppsWithIntervalInMillis: List<Boolean>,
    val notificationsQuality: Int,
)
