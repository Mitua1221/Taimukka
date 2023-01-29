package com.arjental.taimukka.presentaion.ui.screens.tabs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.arjental.taimukka.presentaion.ui.components.app.AppContent
import com.arjental.taimukka.presentaion.ui.components.app.ModalNavigationRailDrawer
import com.arjental.taimukka.presentaion.ui.components.app.NavigationType
import com.arjental.taimukka.presentaion.ui.components.app.PermanentNavigationDrawerContent
import com.arjental.taimukka.presentaion.ui.components.navigations.BottomNavigationBar
import com.arjental.taimukka.presentaion.ui.components.navigations.startTab
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalNavigationContentPosition
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalNavigationType

class TabsRootScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(startTab) {
            when (LocalNavigationType.current) {
                NavigationType.BOTTOM_NAVIGATION -> {
                    BottomNavigationBar()
                }
                NavigationType.NAVIGATION_RAIL -> {
                    ModalNavigationRailDrawer()
                }
                NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                    PermanentNavigationDrawer(drawerContent = {
                        PermanentNavigationDrawerContent(
                            selectedDestination = "selectedDestination",
                            navigationContentPosition = LocalNavigationContentPosition.current,
                        )
                    }) {
                        AppContent()
                    }

                }
            }
        }    }
}