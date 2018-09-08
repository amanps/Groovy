package com.amanps.groovy.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeView {

    @Inject
    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        activityComponent().inject(this)
        homePresenter.attachView(this)

        setupView()
        homePresenter.buildHomePage()
    }

    private fun setupView() {
        recyclerview_home.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = HomeAdapter(this@HomeActivity)
        }
    }

    override fun displayPrograms(sectionedPrograms: LinkedHashMap<Int, List<Program>>) {
        (recyclerview_home.adapter as HomeAdapter).sectionedDataMap = sectionedPrograms
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detachView()
    }
}
