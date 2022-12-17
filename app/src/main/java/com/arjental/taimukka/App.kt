package com.arjental.taimukka

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    var appComponent: AppComponent? = null
        private set

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = buildComponent()
        return appComponent as AppComponent
    }

    protected fun buildComponent(): AppComponent {
        val appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
        return appComponent
    }


}