package com.arjental.taimukka

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.arjental.taimukka.presentaion.ui.screens.app_list.AppListTab
import com.arjental.taimukka.presentaion.ui.screens.main.MainTab
import com.arjental.taimukka.presentaion.ui.theme.TaimukkaTheme
import com.arjental.taimukka.other.utils.components.activity.TaimukkaDaggerActivity
import com.arjental.taimukka.other.utils.factories.viewmodel.Inject


class TaimukkaActivity : TaimukkaDaggerActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightNavigationBars = true

        setContent {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//
//                    .statusBarsPadding().navigationBarsPadding()
//                    .background(Color.Green)
//            ) {
//
//            }
            TaimukkaTheme {
                Inject(viewModelFactory) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                            .navigationBarsPadding(),
                    ) {
                        TabNavigator(MainTab) {
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
                                        TabNavigationItem(MainTab)
                                        TabNavigationItem(AppListTab)
                                    }
                                }
                            )
                        }
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

        NavigationBarItem(
            selected = tabNavigator.current.key == tab.key,
            onClick = { tabNavigator.current = tab },
            icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
        )
    }


}



