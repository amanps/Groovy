package com.amanps.groovy.ui.detail

import android.os.Bundle
import android.util.Log
import com.amanps.groovy.R
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import com.amanps.groovy.util.EXTRA_PROGRAM_ID
import com.amanps.groovy.util.EXTRA_PROGRAM_TYPE
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailView {

    val TAG = "DetailActivity"

    @Inject lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        activityComponent().inject(this)
        detailPresenter.attachView(this)

        val programId = intent.getIntExtra(EXTRA_PROGRAM_ID, -1)
        val programType = intent.getIntExtra(EXTRA_PROGRAM_TYPE, -1)

        if (programId == -1 || programType == -1) {
            throw Exception("Extras to DetailActivity are malformed.")
        }

        detailPresenter.buildDetailsPage(programId, programType)
    }

    override fun displayProgramDetails(program: Program) {
        Log.d(TAG, "program to display: $program")
    }

    override fun displayError(messageResId: Int) {
        Log.d(TAG, getString(messageResId))
    }
}