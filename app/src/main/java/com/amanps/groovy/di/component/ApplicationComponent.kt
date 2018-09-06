package com.amanps.groovy.di.component

import android.content.Context
import com.amanps.groovy.GroovyApplication
import com.amanps.groovy.data.network.ProgramService
import com.amanps.groovy.di.module.ApplicationModule
import com.amanps.groovy.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    val programService: ProgramService
    val applicationContext: Context

    fun inject(application: GroovyApplication)

}