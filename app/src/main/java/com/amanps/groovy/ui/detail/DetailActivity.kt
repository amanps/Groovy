package com.amanps.groovy.ui.detail

import android.os.Bundle
import com.amanps.groovy.R
import com.amanps.groovy.ui.base.BaseActivity
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailView {

    @Inject lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        activityComponent().inject(this)
        detailPresenter.attachView(this)


    }

}