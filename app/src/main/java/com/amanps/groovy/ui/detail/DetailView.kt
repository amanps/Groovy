package com.amanps.groovy.ui.detail

import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BaseView

interface DetailView : BaseView {

    fun displayProgramDetails(program: Program)

    fun displayError(messageRedId: Int)

}