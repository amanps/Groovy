package com.amanps.groovy.ui.home

import android.os.Bundle
import android.util.Log
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeView {

    @Inject
    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        activityComponent().inject(this)
        homePresenter.attachView(this)

        homePresenter.loadPopularPrograms()
    }

    override fun displayPrograms(programs: List<Program>) {
        Log.d("Aman", "Programs: $programs")
    }
}
