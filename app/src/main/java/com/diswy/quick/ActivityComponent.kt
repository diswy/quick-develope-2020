package com.diswy.quick

import com.diswy.common.di.AppComponent
import com.diswy.foundation.di.ano.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}