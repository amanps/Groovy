package com.amanps.groovy.util

import com.amanps.groovy.BuildConfig
import com.amanps.groovy.R

const val BASE_URL = "http://api.themoviedb.org/3/"
val API_KEY = BuildConfig.API_KEY

val MOVIE = "movie"
val TV_SHOW = "tv"

val TYPE_MOVIE = 98
val TYPE_TV_SHOW = 99

class HomePageViewTypes {
    companion object {
        const val POPULAR_MOVIES = 0
        const val POPULAR_TV_SHOWS = 1
        const val MOVIES_RELEASED_THIS_YEAR = 2
        const val TV_SHOWS_RELEASED_THIS_YEAR = 3

        fun getSectionNameResourceId(homePageViewType: Int) : Int {
            return when (homePageViewType) {
                POPULAR_MOVIES -> R.string.section_popular_movies
                POPULAR_TV_SHOWS -> R.string.section_popular_tv_shows
                MOVIES_RELEASED_THIS_YEAR -> R.string.section_movies_released_this_year
                TV_SHOWS_RELEASED_THIS_YEAR -> R.string.section_tv_shows_released_this_year
                else -> { throw IllegalArgumentException("homePageViewType to getSectionNameResourceId is faulty.") }
            }
        }
    }
}