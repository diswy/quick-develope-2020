package com.diswy.common

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.diswy.common.di.AppComponent
import com.diswy.common.di.DaggerAppComponent
import javax.inject.Inject

class App : Application() {

    companion object {
        lateinit var instance: App
            private set

        lateinit var appComponent: AppComponent
            private set
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }
}