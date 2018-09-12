package com.amanps.groovy.ui.detail

import com.amanps.groovy.R
import com.amanps.groovy.data.DataManager
import com.amanps.groovy.ui.base.BasePresenter
import com.amanps.groovy.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter @Inject constructor() : BasePresenter<DetailView>() {

    @Inject lateinit var dataManager: DataManager

    fun buildDetailsPage(programId: Int, groovyProgramType: Int) {
        checkViewAttached()
        val programType = Util.getProgramTypeFromGroovyType(groovyProgramType)
        dataManager.fetchProgramDetails(programId, programType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view!!.displayProgramDetails(it)
                }, {
                    view!!.displayError(R.string.error_program_unavailable)
                })
    }

    fun buildCastSection(programId: Int, groovyProgramType: Int) {
        checkViewAttached()
        val programType = Util.getProgramTypeFromGroovyType(groovyProgramType)
        dataManager.fetchCastForProgram(programType, programId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view!!.displayCastSection(it)
                }, {
                    view!!.displayError(R.string.error_cast_unavailable)
                })
    }

}