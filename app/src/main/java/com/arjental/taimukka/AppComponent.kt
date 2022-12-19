package com.arjental.taimukka

import android.app.Application
import com.arjental.taimukka.other.di.DomainModule
import com.arjental.taimukka.other.di.ScreenModule
import com.arjental.taimukka.other.di.VMModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        VMModule::class,
        DomainModule::class,
        ScreenModule::class,
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)
    fun inject(app: App)
    //fun inject(taimukkaDaggerActivity: TaimukkaDaggerActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}