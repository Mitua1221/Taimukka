package com.arjental.taimukka.presentaion.ui.components.navigations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import com.arjental.taimukka.presentaion.ui.screens.app_list.AppListTab
import com.arjental.taimukka.presentaion.ui.screens.main.MainTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .navigationBarsPadding(),
    ) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.surfaceTint,
                        tonalElevation = 3.dp
                    ) {
                        TabNavigationItem(MainTab())
                        TabNavigationItem(AppListTab())
                    }
                }
            )
    }
}

