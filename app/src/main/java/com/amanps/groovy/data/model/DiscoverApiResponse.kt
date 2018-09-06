package com.amanps.groovy.data.model

data class DiscoverApiResponse(val page: Int,
                               val totalResults: Int,
                               val totalPages: Int,
                               val results: List<Program>)