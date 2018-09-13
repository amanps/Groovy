package com.amanps.groovy.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import com.amanps.groovy.ui.detail.DetailActivityIntent
import com.amanps.groovy.util.NetworkUtils
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

    override fun displayPrograms(sectionedPrograms: List<HomeListSectionModel>) {
        (recyclerview_home.adapter as HomeAdapter).sections = sectionedPrograms
    }

    override fun displayBannerImages(bannerUrls: List<String>) {
        if (bannerUrls.isEmpty()) {
            viewpager_banner.visibility = View.GONE
            return
        }
        viewpager_banner.adapter = BannerPagerAdapter(this, bannerUrls)
    }

    private fun handleProgramClicked(program: Program) {
        startActivity(DetailActivityIntent(program))
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detachView()
    }
}
