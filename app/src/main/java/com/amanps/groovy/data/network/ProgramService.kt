package com.amanps.groovy.data.network

import com.amanps.groovy.data.model.DiscoverApiResponse
import com.amanps.groovy.util.BASE_URL
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProgramService {

    @GET("discover/{programType}?sort_by=popularity.desc" +
            "&language=en-US&include_adult=false&include_video=false")
    fun getPopularPrograms(
            @Path("programType") programType: String,
            @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

    @GET("discover/{programType}?sort_by=vote_average.desc" +
            "&language=en-US&include_adult=false&include_video=false")
    fun getTopRatedPrograms(
        @Path("programType") programType: String,
        @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

    @GET("discover/movie?sort_by=popularity.desc" +
            "&language=en-US&include_adult=false&include_video=false")
    fun getMoviesReleasedInYear(
            @Query("primary_release_year") year: String,
            @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

    @GET("discover/tv?sort_by=popularity.desc&include_null_first_air_dates=false" +
            "&language=en-US&include_adult=false&include_video=false")
    fun getTvShowsReleasedInYear(
            @Query("first_air_date_year") year: String,
            @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

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