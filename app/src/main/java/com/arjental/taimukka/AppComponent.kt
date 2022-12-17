package com.arjental.taimukka

import android.app.Application
import com.arjental.taimukka.di.DomainModule
import com.arjental.taimukka.di.ScreenModule
import com.arjental.taimukka.di.VMModule
import com.arjental.taimukka.utils.components.activity.TaimukkaDaggerActivity
import com.arjental.taimukka.utils.factories.viewmodel.TaimukkaViewModelFactory
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