package com.arjental.taimukka

import android.app.Application
import android.content.Context
import com.arjental.taimukka.other.di.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DomainModule::class,
        ScreenModule::class,
        DataModule::class,
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)
    fun inject(app: App)
    fun inject(context: Context)
    //fun inject(taimukkaDaggerActivity: TaimukkaDaggerActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}