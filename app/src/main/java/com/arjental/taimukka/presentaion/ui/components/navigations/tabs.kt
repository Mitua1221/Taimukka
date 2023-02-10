package com.arjental.taimukka.presentaion.ui.components.navigations

import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.AppListTab
import com.arjental.taimukka.presentaion.ui.screens.tabs.control.ControlTab
import com.arjental.taimukka.presentaion.ui.screens.tabs.settings.SettingsTab
import com.arjental.taimukka.presentaion.ui.screens.tabs.stats.StatsTab

val startTab = SettingsTab()

val navigationTabs = listOf(
    StatsTab(),
    AppListTab(),
    ControlTab(),
    startTab,
)