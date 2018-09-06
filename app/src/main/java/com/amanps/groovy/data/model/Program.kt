package com.amanps.groovy.data.model

data class Program(val id: Int,
                   val title: String,
                   val posterPath: String?,
                   val adult: Boolean,
                   val overview: String,
                   val releaseDate: String,
                   val genreIds: List<Int>,
                   val originalTitle: String,
                   val originalLanguage: String,
                   val backdropPath: String?,
                   val popularity: Float,
                   val voteCount: Int,
                   val video: Boolean,
                   val voteAverage: Float)