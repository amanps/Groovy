package com.amanps.groovy.ui.home

import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseView

interface HomeView : BaseView {

    /**
     * Display a sectioned list of programs defined by an ordered map of
     * section type (HomePageViewType) mapped to a list of programs.
     */
    fun displayPrograms(sectionedPrograms: LinkedHashMap<Int, List<Program>>)

}