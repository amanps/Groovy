package com.amanps.groovy.data

import android.util.Log
import com.amanps.groovy.data.model.DiscoverApiResponse
import com.amanps.groovy.data.model.Program
import com.amanps.groovy.data.network.ProgramService
import com.amanps.groovy.util.API_KEY
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
                    Log.d(TAG, "Fetching popular $programType failed.")
                    DiscoverApiResponse.empty()
                }.map {
                    it.results.map {
                        it.apply {
                            groovyProgramType = Util.getGroovyTypeFromProgramType(programType)
                        }
                    }
                }
    }

}