package com.arjental.taimukka

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.arjental.taimukka.ui.screens.app_list.AppListTab
import com.arjental.taimukka.ui.screens.main.MainTab
import com.arjental.taimukka.ui.theme.TaimukkaTheme
import com.arjental.taimukka.utils.components.activity.TaimukkaDaggerActivity
import com.arjental.taimukka.utils.factories.viewmodel.Inject

class TaimukkaActivity : TaimukkaDaggerActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView
        setContent {
            TaimukkaTheme {
                Inject(viewModelFactory) {
                    TabNavigator(MainTab) {
                        Scaffold(
                            content = {
                                CurrentTab()
                            },
                            bottomBar = {
                                BottomNavigation {
                                    TabNavigationItem(MainTab)
                                    TabNavigationItem(AppListTab)
                                }
                            }
                        )
                    }


//                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                        MainScreen("Android")
//                    }
                }
            }
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current.key == tab.key,
            onClick = { tabNavigator.current = tab },
            icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
        )
    }

}



