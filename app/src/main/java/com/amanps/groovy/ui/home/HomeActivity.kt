package com.amanps.groovy.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import com.amanps.groovy.ui.detail.DetailActivity
import com.amanps.groovy.util.EXTRA_PROGRAM_ID
import com.amanps.groovy.util.EXTRA_PROGRAM_TYPE
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

    private fun handleProgramClicked(program: Program) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_PROGRAM_ID, program.id)
        intent.putExtra(EXTRA_PROGRAM_TYPE, program.groovyProgramType)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.detachView()
    }
}
