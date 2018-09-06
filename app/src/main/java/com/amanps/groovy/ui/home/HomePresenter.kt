package com.amanps.groovy.ui.home

import com.amanps.groovy.data.DataManager
import com.amanps.groovy.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor() : BasePresenter<HomeView>() {

    @Inject lateinit var dataManager: DataManager

    private var subscription: Disposable? = null

    fun loadPopularPrograms() {
        checkViewAttached()
        subscription = dataManager.fetchPopularPrograms("movie")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    view!!.displayPrograms(it)
                }
    }

}