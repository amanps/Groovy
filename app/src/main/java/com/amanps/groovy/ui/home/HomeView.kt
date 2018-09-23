package com.amanps.groovy.ui.home

import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseView

interface HomeView : BaseView {

    /**
     * Display a sectioned list of programs defined by list of HomeListSectionModel.
     */
    fun displayHomePageSections(sectionedPrograms: List<HomeListSectionModel>)

    // Show the loading shimmer layout
    fun displayLoading()

    fun displayError()

}