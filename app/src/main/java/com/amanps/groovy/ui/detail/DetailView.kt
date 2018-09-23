package com.amanps.groovy.ui.detail

import com.amanps.groovy.data.model.CastModel
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseView

interface DetailView : BaseView {

    fun displayLoading()

    fun displayProgramDetails(program: Program)

    fun displayError(messageResId: Int)

    fun displayCastSection(castList: List<CastModel>)

    fun displayRecommendationsSection(recommendedPrograms: List<Program>)

    fun displaySimilarProgramsSection(similarPrograms: List<Program>)
}