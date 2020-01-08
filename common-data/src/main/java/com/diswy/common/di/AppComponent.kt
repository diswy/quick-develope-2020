package com.diswy.common.di

import android.app.Application
import com.diswy.common.App
import com.diswy.foundation.tools.UpdateHelper
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun provideApp(): App

    fun provideGson(): Gson

    fun provideUpdateHelper(): UpdateHelper
}