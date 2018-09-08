package com.amanps.groovy.util

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
    }
}