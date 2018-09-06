package com.amanps.groovy.ui.home

import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseView

interface HomeView : BaseView {

    fun displayPrograms(programs: List<Program>)

}