package com.amanps.groovy.ui.home

import android.util.Log
import com.amanps.groovy.R
import com.amanps.groovy.data.DataManager
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BasePresenter
import com.amanps.groovy.util.HomePageViewTypes
import com.amanps.groovy.util.MOVIE
import com.amanps.groovy.util.TV_SHOW
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor() : BasePresenter<HomeView>() {

    val TAG = "HomePresenter"

    @Inject lateinit var dataManager: DataManager

    val homePageSectionTypes = arrayOf(
            HomePageViewTypes.POPULAR_MOVIES,
            HomePageViewTypes.POPULAR_TV_SHOWS
    )

    /**
     * Fetches lists of programs to be displayed in the home page UI
     * and calls view.displayPrograms() with the received data.
     */
    fun buildHomePage() {
        checkViewAttached()
        getHomePageDataSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view!!.displayPrograms(it)
                }, {
                    Log.d(TAG, "Fetching home page data error. ")
                })
                .let { compositeDisposable?.add(it) }
    }

    private fun getHomePageDataSingle() : Single<LinkedHashMap<Int, List<Program>>> {
        val popularMoviesObservable = dataManager.fetchPopularProgramsOfType(MOVIE)
        val popularTvShowsObservable = dataManager.fetchPopularProgramsOfType(TV_SHOW)

        return Single.zip(popularMoviesObservable, popularTvShowsObservable,
                BiFunction<List<Program>, List<Program>, LinkedHashMap<Int, List<Program>>> { popularMovies, popularTvShows ->
                    getSectionedPrograms(listOf(popularMovies, popularTvShows))
                })
    }

    private fun getSectionedPrograms(programs: List<List<Program>>) : LinkedHashMap<Int, List<Program>> {
        val map = LinkedHashMap<Int, List<Program>>()
        programs.forEachIndexed { index, programs ->
            map[homePageSectionTypes[index]] = programs
        }
        return map
    }
}