package com.amanps.groovy.ui.detail

import com.amanps.groovy.data.DataManager
import com.amanps.groovy.ui.base.BasePresenter
import javax.inject.Inject

class DetailPresenter @Inject constructor() : BasePresenter<DetailView>() {

    @Inject lateinit var dataManager: DataManager

}