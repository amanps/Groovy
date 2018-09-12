package com.amanps.groovy.ui.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.amanps.groovy.R
import com.amanps.groovy.data.model.CastModel
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import com.amanps.groovy.util.EXTRA_PROGRAM_ID
import com.amanps.groovy.util.EXTRA_PROGRAM_TITLE
import com.amanps.groovy.util.EXTRA_PROGRAM_TYPE
import com.amanps.groovy.util.NetworkUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.program_summary.*
import kotlinx.android.synthetic.main.program_summary.view.*
import kotlinx.android.synthetic.main.recyclerview_horizontal_sectioned.*
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailView {

    val TAG = "DetailActivity"
    val BACKDROP_IMAGE_SIZE = "w780"

    @Inject lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        activityComponent().inject(this)
        detailPresenter.attachView(this)

        val programId = intent.getIntExtra(EXTRA_PROGRAM_ID, -1)
        val programType = intent.getIntExtra(EXTRA_PROGRAM_TYPE, -1)
        val programTitle = intent.getStringExtra(EXTRA_PROGRAM_TITLE)

        setupToolbar(programTitle)

        if (programId == -1 || programType == -1) {
            throw Exception("Extras to DetailActivity are malformed.")
        }

        detailPresenter.buildDetailsPage(programId, programType)
        detailPresenter.buildCastSection(programId, programType)
    }

    private fun setupToolbar(programTitle: String) {
        textview_program_title.text = programTitle
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun displayProgramDetails(program: Program) {
        Glide.with(this)
                .load(NetworkUtils.getPosterImageUrl(program.backdrop_path ?: "", BACKDROP_IMAGE_SIZE))
                .apply(RequestOptions()
                        .placeholder(R.drawable.image_placeholder)
                        .centerCrop())
                .into(imageview_poster)

        program_summary.summary.text = program.overview
        program_summary.title.text = getString(R.string.summary_section_title)
        program_summary.release_date.text = program.release_date ?: program.first_air_date
        program_summary.genres.text = program.genres?.map { it.name }?.joinToString()
    }

    override fun displayCastSection(castList: List<CastModel>) {
        recyclerview_horizontal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CastRecyclerAdapter(context, castList, this@DetailActivity::handleCastClicked)
        }
        textview_section_name.text = getString(R.string.section_cast)
    }

    override fun displayError(messageResId: Int) {
        Log.d(TAG, getString(messageResId))
    }

    private fun handleCastClicked(cast: CastModel) {
        Log.d(TAG, "Cast clicked : $cast")
    }
}