package com.amanps.groovy.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import com.amanps.groovy.ui.detail.DetailActivityIntent
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.error_screen.*
import kotlinx.android.synthetic.main.error_screen.view.*
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

        error_layout.button_try_again.setOnClickListener {
            error_layout.visibility = View.GONE
            displayShimmer()
            homePresenter.buildHomePage()
        }
    }

    override fun displayHomePageSections(sectionedPrograms: List<HomeListSectionModel>) {
        dismissShimmer()
        error_layout.visibility = View.GONE
        (recyclerview_home.adapter as HomeAdapter).apply {
            sections = sectionedPrograms.toMutableList()
            notifyDataSetChanged()
        }
        recyclerview_home.visibility = View.VISIBLE
    }

    override fun displayLoading() {
        displayShimmer()
        recyclerview_home.visibility = View.GONE
        error_layout.visibility = View.GONE
    }

    override fun displayError() {
        error_layout.visibility = View.VISIBLE
        dismissShimmer()
        recyclerview_home.visibility = View.GONE
    }

    private fun handleProgramClicked(program: Program) {
        startActivity(DetailActivityIntent(program))
    }

    private fun dismissShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
    }

    private fun displayShimmer() {
        shimmer_layout.visibility = View.VISIBLE
        shimmer_layout.startShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detachView()
    }
}
