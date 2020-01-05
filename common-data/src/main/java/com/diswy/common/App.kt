package com.diswy.common

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.diswy.common.di.DaggerAppComponent
import javax.inject.Inject

class App : Application() {

    companion object {
        lateinit var instance: App
            private set


    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        instance = this
        DaggerAppComponent.builder().application(this).build().inject(this)
    }
}