package com.amanps.groovy.data.network

import com.amanps.groovy.data.model.DiscoverApiResponse
import com.amanps.groovy.util.BASE_URL
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProgramService {

    @GET("discover/{programType}?sort_by=popularity.desc")
    fun getPopularPrograms(
            @Path("programType") programType: String,
            @Query("api_key") apiKey: String) : Observable<DiscoverApiResponse>

    object Factory {
        fun buildService(): ProgramService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()
            return retrofit.create(ProgramService::class.java)
        }
    }
}