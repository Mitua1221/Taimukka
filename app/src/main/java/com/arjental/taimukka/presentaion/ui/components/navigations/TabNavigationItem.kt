package com.arjental.taimukka.presentaion.ui.components.navigations

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val currentTab = LocalTabNavigator.current

    val isSelected = currentTab.current.key == tab.key
    val color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    NavigationBarItem(
        selected = isSelected,
        onClick = { currentTab.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title, tint = color) },
        label = {
            Text(
                style = MaterialTheme.typography.labelMedium,
                color = color,
                text = tab.options.title
            )
        }
    )
}
