package com.amanps.groovy.data.model

data class DiscoverApiResponse(val page: Int,
                               val totalResults: Int,
                               val totalPages: Int,
                               val results: List<Program>) {

    companion object {
        fun empty() = DiscoverApiResponse(0, 0, 0, emptyList())
    }

}