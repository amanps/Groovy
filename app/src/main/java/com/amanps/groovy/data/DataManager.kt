package com.amanps.groovy.data

import android.util.Log
import com.amanps.groovy.data.model.CastCrewResponseModel
import com.amanps.groovy.data.model.CastModel
import com.amanps.groovy.data.model.DiscoverApiResponse
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.data.network.ProgramService
import com.amanps.groovy.util.API_KEY
import com.amanps.groovy.util.MOVIE
import com.amanps.groovy.util.TV_SHOW
import com.amanps.groovy.util.Util
import io.reactivex.Single
import javax.inject.Inject

class DataManager @Inject constructor() {

    val TAG = "DataManager"

    @Inject
    lateinit var programService: ProgramService

    /**
     * Makes an API call and fetches a list of programs of the specified type as a Single.
     * In case of an error, returns a Single with an empty list.
     */
    fun fetchPopularProgramsOfType(programType: String) : Single<List<Program>> {
        return programService.getPopularPrograms(programType, API_KEY)
                .onErrorReturn {
                    Log.e(TAG, "Fetching popular $programType failed.")
                    DiscoverApiResponse.empty()
                }.map { getTypeTaggedResults(it, programType) }
    }

    fun fetchTopRatedProgramsOfType(programType: String) : Single<List<Program>> {
        return programService.getTopRatedPrograms(programType, API_KEY)
                .onErrorReturn {
                    Log.e(TAG, "Fetching top rated $programType failed.")
                    DiscoverApiResponse.empty()
                }.map { getTypeTaggedResults(it, programType) }
    }

    fun fetchProgramsReleasedInYear(programType: String, year: String) : Single<List<Program>> {
        val programsSingle = when (programType) {
            MOVIE -> programService.getMoviesReleasedInYear(year, API_KEY)
            TV_SHOW -> programService.getTvShowsReleasedInYear(year, API_KEY)
            else -> { throw IllegalArgumentException("Program type passed to fetchProgramsReleasedInYear is faulty.") }
        }
        return programsSingle
                .onErrorReturn {
                    Log.e(TAG, "Fetching program type $programType released in year $year failed.")
                    DiscoverApiResponse.empty()
                }.map { getTypeTaggedResults(it, programType) }
    }

    private fun getTypeTaggedResults(apiResponse: DiscoverApiResponse, programType: String) : List<Program> {
        return apiResponse.results.map {
            it.apply {
                groovyProgramType = Util.getGroovyTypeFromProgramType(programType)
            }
        }
    }

    fun fetchProgramDetails(programId: Int, programType: String) : Single<Program> {
        return programService.getProgramDetails(programType, programId, API_KEY)
                .map {
                    it.apply { groovyProgramType = Util.getGroovyTypeFromProgramType(programType) }
                }
    }

    fun fetchCastForProgram(programType: String, programId: Int) : Single<List<CastModel>> {
        return programService.getCastCrewForProgram(programType, programId, API_KEY)
                .map {
                    it.cast
                }
    }

    fun fetchRecommendationsForProgram(programType: String, programId: Int) : Single<List<Program>> {
        return programService.getRecommendationsForProgram(programType, programId, API_KEY)
                .map { getTypeTaggedResults(it, programType) }
    }

}