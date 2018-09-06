package com.amanps.groovy.data

import com.amanps.groovy.data.model.Program
import com.amanps.groovy.data.network.ProgramService
import com.amanps.groovy.util.API_KEY
import io.reactivex.Observable
import javax.inject.Inject

class DataManager @Inject constructor(val programService: ProgramService) {

    fun fetchPopularPrograms(programType: String) : Observable<List<Program>> {
        return programService.getPopularPrograms(programType, API_KEY)
                .map {
                    it.results
                }
    }

}