package com.amanps.groovy.di.component

import com.amanps.groovy.di.ActivityScope
import com.amanps.groovy.ui.home.HomeActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class])
interface ActivityComponent {

    fun inject(homeActivity: HomeActivity)

}