package com.amanps.groovy.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule constructor(val context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

}