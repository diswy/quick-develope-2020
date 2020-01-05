package com.diswy.quick

import com.diswy.common.di.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}