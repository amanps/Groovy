package com.amanps.groovy.util

import com.amanps.groovy.R
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun getGroovyTypeFromProgramType(programType: String) : Int {
            return when (programType) {
                MOVIE -> TYPE_MOVIE
                TV_SHOW -> TYPE_TV_SHOW
                else -> {
                    throw IllegalArgumentException("programType to getGroovyTypeFromProgramType" +
                            "is faulty.")
                }
            }
        }

        fun getProgramTypeFromGroovyType(groovyProgramType: Int) : String {
            return when (groovyProgramType) {
                TYPE_MOVIE -> MOVIE
                TYPE_TV_SHOW -> TV_SHOW
                else -> {
                    throw IllegalArgumentException("groovyProgramType to getProgramTypeFromGroovyType" +
                            "is faulty.")
                }
            }
        }

        fun getProgramTypeName(groovyProgramType: Int) : String {
            return when (groovyProgramType) {
                TYPE_MOVIE -> MOVIES
                TYPE_TV_SHOW -> TV_SHOWS
                else -> {
                    throw IllegalArgumentException("groovyProgramType to getProgramTypeName" +
                            "is faulty.")
                }
            }
        }

        fun getReleaseTextResId(releaseDate: String) : Int {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(releaseDate)
            return if (date.after(Date())) R.string.releasing else R.string.released
        }

        fun getPrettyReleaseDate(releaseDate: String) : String {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(releaseDate)
            val format = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)
            return format.format(date)
        }
    }
}