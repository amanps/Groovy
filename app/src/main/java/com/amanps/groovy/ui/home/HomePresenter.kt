package com.amanps.groovy.ui.home

import android.util.Log
import com.amanps.groovy.R
import com.amanps.groovy.data.DataManager
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BasePresenter
import com.amanps.groovy.util.MOVIE
import com.amanps.groovy.util.TV_SHOW
import com.amanps.groovy.util.VIEW_TYPE_HORIZONTAL_LIST
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
        val homePageHorizontalSections = arrayOf(
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_popular_movies),
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_popular_tv_shows),
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_movies_released_this_year),
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_tv_shows_released_this_year)
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
                    Log.e(TAG, "Fetching home page data error. ")
                })
                .let { compositeDisposable?.add(it) }
    }

    private fun getHomePageDataSingle() : Single<List<HomeListSectionModel>> {
        val popularMoviesSingle = dataManager.fetchPopularProgramsOfType(MOVIE)
        val popularTvShowsSingle = dataManager.fetchPopularProgramsOfType(TV_SHOW)
        val moviesReleasedThisYearSingle = dataManager.fetchProgramsReleasedInYear(MOVIE,
                Calendar.getInstance().get(Calendar.YEAR).toString())
        val tvShowsReleasedThisYearSingle = dataManager.fetchProgramsReleasedInYear(TV_SHOW,
                Calendar.getInstance().get(Calendar.YEAR).toString())

        return Single.zip(popularMoviesSingle, popularTvShowsSingle, moviesReleasedThisYearSingle, tvShowsReleasedThisYearSingle,

                Function4<List<Program>, List<Program>, List<Program>, List<Program>, List<HomeListSectionModel>> {

                    popularMovies, popularTvShows, moviesReleasedThisYear, tvShowsReleasedThisYear ->
                    getSectionedPrograms(listOf(popularMovies, popularTvShows, moviesReleasedThisYear, tvShowsReleasedThisYear))

                })
    }

    private fun getSectionedPrograms(programs: List<List<Program>>) : List<HomeListSectionModel> {
        if (programs.size != homePageHorizontalSections.size)
            throw Exception("Not all home page section types have been defined in the HomePresenter.")

        val sectionsList = mutableListOf<HomeListSectionModel>()

        programs.forEachIndexed { index, programList ->
            sectionsList.add(HomeListSectionModel(
                    homePageHorizontalSections[index].first,
                    homePageHorizontalSections[index].second,
                    programList
            ))
        }
        return sectionsList
    }
}