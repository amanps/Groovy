package com.amanps.groovy.util

import com.amanps.groovy.BuildConfig

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
    }
}