package com.amanps.groovy.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.amanps.groovy.R
import com.amanps.groovy.data.model.CastModel
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseActivity
import com.amanps.groovy.ui.common.HorizontalProgramRecyclerAdapter
import com.amanps.groovy.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.program_summary.*
import kotlinx.android.synthetic.main.program_summary.view.*
import kotlinx.android.synthetic.main.recyclerview_horizontal_sectioned.view.*
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailView {

    private val TAG = "DetailActivity"
    private val BACKDROP_IMAGE_SIZE = "w780"
    private var programType: Int = -1

    @Inject lateinit var detailPresenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        activityComponent().inject(this)
        detailPresenter.attachView(this)

        val programId = intent.getIntExtra(EXTRA_PROGRAM_ID, -1)
        programType = intent.getIntExtra(EXTRA_PROGRAM_TYPE, -1)
        val programTitle = intent.getStringExtra(EXTRA_PROGRAM_TITLE)

        setupToolbar(programTitle)

        if (programId == -1 || programType == -1) {
            throw Exception("Extras to DetailActivity are malformed.")
        }

        detailPresenter.buildDetailsPage(programId, programType)
    }

    private fun setupToolbar(programTitle: String) {
        textview_program_title.text = programTitle
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun displayProgramDetails(program: Program) {
        dismissShimmer()
        content_layout.visibility = View.VISIBLE
        Glide.with(this)
                .load(NetworkUtils.getPosterImageUrl(program.backdrop_path ?: "", BACKDROP_IMAGE_SIZE))
                .apply(RequestOptions()
                        .placeholder(R.drawable.image_placeholder)
                        .centerCrop())
                .into(imageview_poster)

        program_summary.summary.text = program.overview

        val releaseDate = program.release_date ?: program.first_air_date
        if (releaseDate.isNullOrBlank()) {
            program_summary.release_date.visibility = View.GONE
        } else {
            program_summary.release_date.text = getString(R.string.program_release_date,
                    getString(Util.getReleaseTextResId(releaseDate!!)), Util.getPrettyReleaseDate(releaseDate))
        }

        program_summary.genres.text = program.genres?.map { it.name }?.joinToString()

        if (program.vote_average?.compareTo(0.0) == 0) {
            program_summary.layout_rating.visibility = View.GONE
        } else {
            program_summary.rating.text = getString(R.string.program_rating, program.vote_average.toString())
        }
    }

    override fun displayCastSection(castList: List<CastModel>) {
        if (castList.isEmpty()) {
            recyclerview_cast.visibility = View.GONE
            return
        }
        recyclerview_cast.recyclerview_horizontal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CastRecyclerAdapter(context, castList, this@DetailActivity::handleCastClicked)
        }
        recyclerview_cast.textview_section_name.text = getString(R.string.section_cast)
    }

    override fun displayRecommendationsSection(recommendedPrograms: List<Program>) {
        if (recommendedPrograms.isEmpty()) {
            recyclerview_recommendations.visibility = View.GONE
            return
        }
        recyclerview_recommendations.recyclerview_horizontal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HorizontalProgramRecyclerAdapter(context, recommendedPrograms,
                    this@DetailActivity::handleProgramClicked, true)
        }
        recyclerview_recommendations.textview_section_name.text = getString(R.string.section_recommendations)
    }

    override fun displaySimilarProgramsSection(similarPrograms: List<Program>) {
        if (similarPrograms.isEmpty()) {
            recyclerview_similar.visibility = View.GONE
            return
        }
        recyclerview_similar.recyclerview_horizontal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HorizontalProgramRecyclerAdapter(context, similarPrograms,
                    this@DetailActivity::handleProgramClicked, true)
        }
        recyclerview_similar.textview_section_name.text = getString(R.string.section_similar, Util.getProgramTypeName(programType))
    }

    override fun displayError(messageResId: Int) {
        Log.d(TAG, getString(messageResId))
    }

    private fun handleCastClicked(cast: CastModel) {
        Log.d(TAG, "Cast clicked : $cast")
    }

    private fun handleProgramClicked(program: Program) {
        startActivity(DetailActivityIntent(program))
    }

    override fun displayLoading() {
        shimmer_layout.visibility = View.VISIBLE
        content_layout.visibility = View.GONE
    }

    private fun dismissShimmer() {
        shimmer_layout.stopShimmer()
        shimmer_layout.visibility = View.GONE
    }

}

fun Context.DetailActivityIntent(program: Program): Intent {
    return Intent(this, DetailActivity::class.java).apply {
        putExtra(EXTRA_PROGRAM_ID, program.id)
        putExtra(EXTRA_PROGRAM_TYPE, program.groovyProgramType)
        putExtra(EXTRA_PROGRAM_TITLE, program.title ?: program.name)
    }
}