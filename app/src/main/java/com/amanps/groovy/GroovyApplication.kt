package com.amanps.groovy

import android.app.Application
import com.amanps.groovy.di.component.ApplicationComponent
import com.amanps.groovy.di.component.DaggerApplicationComponent
import com.amanps.groovy.di.module.ApplicationModule
import com.amanps.groovy.di.module.NetworkModule

class GroovyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule())
                .build()

        applicationComponent.inject(this)
    }

}