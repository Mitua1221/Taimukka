package com.arjental.taimukka.presentaion.ui.components.navigations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .navigationBarsPadding(),
    ) {

            Scaffold(
                content = { padding ->
                    Box(modifier = Modifier.padding(bottom = padding.calculateBottomPadding())) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.secondaryContainer,
                        tonalElevation = 2.dp
                    ) {
                        navigationTabs.forEach {
                            TabNavigationItem(it)
                        }
                    }
                },
            )
    }
}

