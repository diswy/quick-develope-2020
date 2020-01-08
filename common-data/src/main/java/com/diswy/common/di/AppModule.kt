package com.diswy.common.di

import com.diswy.common.App
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    fun provideApplication(): App {
        return App.instance
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}