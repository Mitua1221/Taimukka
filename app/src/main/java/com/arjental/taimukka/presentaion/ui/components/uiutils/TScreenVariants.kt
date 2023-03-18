package com.arjental.taimukka.presentaion.ui.components.uiutils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.androidx.AndroidScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab

private interface TScreenInterface {

    @Composable
    fun TContent()

}

@kotlinx.serialization.Serializable
abstract class TTab : Tab, TScreen()

@kotlinx.serialization.Serializable
open class TScreen : TScreenInterface, Screen, ScreenLifecycleProvider {

    /**
     * If you need a lifecycle of parent composable, you must to override it to true
     */

    open val implementParentLifecycle = false

    @Composable
    override fun TContent() {
        throw IllegalStateException("Implement it ")
    }

    override fun getLifecycleOwner(): ScreenLifecycleOwner = AndroidScreenLifecycleOwner.get(this)

    @Composable
    final override fun Content() {
        if (implementParentLifecycle) {
            TContent()
        } else {
            CompositionLocalProvider(
                LocalTScreen provides this,
            ) {
                TContent()
            }
        }
    }


}
