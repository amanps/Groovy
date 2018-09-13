package com.amanps.groovy.ui.home

import android.util.Log
import com.amanps.groovy.R
import com.amanps.groovy.data.DataManager
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.ui.base.BasePresenter
import com.amanps.groovy.util.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor() : BasePresenter<HomeView>() {

    private val TAG = "HomePresenter"

    @Inject lateinit var dataManager: DataManager

    val BANNER_IMAGE_SIZE = "w780"
    val NUMBER_OF_BANNER_IMAGES = 5

    companion object {
        val homePageHorizontalSections = arrayOf(
                Pair(VIEW_TYPE_BANNER_VIEW_PAGER, -1),
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_popular_movies),
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_popular_tv_shows),
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_movies_in_theatres),
                Pair(VIEW_TYPE_HORIZONTAL_LIST, R.string.section_upcoming_movies)
        )
    }

    /**
     * Fetches lists of programs to be displayed in the home page UI
     * and calls view.displayHomePageSections() with the received data.
     */
    fun buildHomePage() {
        checkViewAttached()
        getHomePageDataSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view!!.displayHomePageSections(it)
                }, {
                    Log.e(TAG, "Fetching home page data error.")
                })
                .let { compositeDisposable?.add(it) }
    }

    private fun getHomePageDataSingle() : Single<List<HomeListSectionModel>> {
        val popularMoviesSingle = dataManager.fetchPopularProgramsOfType(MOVIE)
        val popularTvShowsSingle = dataManager.fetchPopularProgramsOfType(TV_SHOW)
        val upcomingMoviesSingle = dataManager.fetchUpcomingMovies()
        val moviesInTheatresSingle = dataManager.fetchMoviesInTheatres()
        val bannerProgramsSingle = getBannerProgramsSingle()

        return Single.zip(popularMoviesSingle, popularTvShowsSingle, moviesInTheatresSingle,
                upcomingMoviesSingle, bannerProgramsSingle,

                Function5<List<Program>, List<Program>, List<Program>, List<Program>, List<Program>, List<HomeListSectionModel>> {

                    popularMovies, popularTvShows, moviesInTheatres, upcomingMovies, bannerPrograms ->
                    getSectionedPrograms(listOf(bannerPrograms,
                            popularMovies,
                            popularTvShows,
                            moviesInTheatres,
                            upcomingMovies))

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

    private fun getBannerProgramsSingle() : Single<List<Program>> {
        val trendingMoviesSingle = dataManager.fetchTrendingPrograms(MOVIE)
        val trendingTvShowsSingle = dataManager.fetchTrendingPrograms(TV_SHOW)

        return Single.zip(trendingMoviesSingle, trendingTvShowsSingle,
                BiFunction<List<Program>, List<Program>, List<Program>> { trendingMovies, trendingTvShows ->
            trendingMovies.zip(trendingTvShows).flatMap {
                listOf(it.first, it.second)
            }.take(NUMBER_OF_BANNER_IMAGES)
        })
    }
}