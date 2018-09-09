package com.amanps.groovy.ui.home

import android.util.Log
import com.amanps.groovy.data.DataManager
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BasePresenter
import com.amanps.groovy.util.HomePageViewTypes
import com.amanps.groovy.util.MOVIE
import com.amanps.groovy.util.TV_SHOW
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomePresenter @Inject constructor() : BasePresenter<HomeView>() {

    private val TAG = "HomePresenter"

    @Inject lateinit var dataManager: DataManager

    companion object {
        val homePageHorizontalSectionTypes = arrayOf(
                HomePageViewTypes.POPULAR_MOVIES,
                HomePageViewTypes.POPULAR_TV_SHOWS,
                HomePageViewTypes.MOVIES_RELEASED_THIS_YEAR,
                HomePageViewTypes.TV_SHOWS_RELEASED_THIS_YEAR
        )
    }

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
        val popularMoviesSingle = dataManager.fetchPopularProgramsOfType(MOVIE)
        val popularTvShowsSingle = dataManager.fetchPopularProgramsOfType(TV_SHOW)
        val moviesReleasedThisYearSingle = dataManager.fetchProgramsReleasedInYear(MOVIE,
                Calendar.getInstance().get(Calendar.YEAR).toString())
        val tvShowsReleasedThisYearSingle = dataManager.fetchProgramsReleasedInYear(TV_SHOW,
                Calendar.getInstance().get(Calendar.YEAR).toString())

        return Single.zip(popularMoviesSingle, popularTvShowsSingle, moviesReleasedThisYearSingle, tvShowsReleasedThisYearSingle,

                Function4<List<Program>, List<Program>, List<Program>, List<Program>, LinkedHashMap<Int, List<Program>>> {

                    popularMovies, popularTvShows, moviesReleasedThisYear, tvShowsReleasedThisYear ->
                    getSectionedPrograms(listOf(popularMovies, popularTvShows, moviesReleasedThisYear, tvShowsReleasedThisYear))

                })
    }

    private fun getSectionedPrograms(programs: List<List<Program>>) : LinkedHashMap<Int, List<Program>> {
        if (programs.size != homePageHorizontalSectionTypes.size)
            throw Exception("Not all home page section types have been defined in the HomePresenter.")

        val map = LinkedHashMap<Int, List<Program>>()
        programs.forEachIndexed { index, programList ->
            map[homePageHorizontalSectionTypes[index]] = programList
        }
        return map
    }
}