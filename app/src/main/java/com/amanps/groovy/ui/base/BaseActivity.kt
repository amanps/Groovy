package com.amanps.groovy.ui.base

import android.support.v7.app.AppCompatActivity
import com.amanps.groovy.GroovyApplication
import com.amanps.groovy.di.component.ActivityComponent
import com.amanps.groovy.di.component.DaggerActivityComponent

open class BaseActivity : BaseView, AppCompatActivity() {

    private var activityComponent: ActivityComponent? = null

    fun activityComponent() : ActivityComponent {
        return activityComponent ?: DaggerActivityComponent.builder()
                .applicationComponent((application as GroovyApplication).applicationComponent)
                .build()
    }

}