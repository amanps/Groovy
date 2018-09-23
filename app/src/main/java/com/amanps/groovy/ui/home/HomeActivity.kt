package com.amanps.groovy.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import com.amanps.groovy.ui.detail.DetailActivityIntent
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
            adapter = HomeAdapter(this@HomeActivity, this@HomeActivity::handleProgramClicked)
        }
    }

    override fun displayHomePageSections(sectionedPrograms: List<HomeListSectionModel>) {
        dimissShimmer()
        (recyclerview_home.adapter as HomeAdapter).apply {
            sections = sectionedPrograms.toMutableList()
            notifyDataSetChanged()
        }
        recyclerview_home.visibility = View.VISIBLE
    }

    override fun showLoading() {
        shimmer_layout.visibility = View.VISIBLE
        recyclerview_home.visibility = View.GONE
    }

    private fun handleProgramClicked(program: Program) {
        startActivity(DetailActivityIntent(program))
    }

    private fun dimissShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detachView()
    }
}
