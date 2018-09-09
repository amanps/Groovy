package com.amanps.groovy.util

class NetworkUtils {

    companion object {
        val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"

        fun getPosterImageUrl(posterPath: String, size: String = "w342") = IMAGE_BASE_URL + size + posterPath
    }

}