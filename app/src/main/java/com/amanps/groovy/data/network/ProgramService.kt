package com.amanps.groovy.data.network

import com.amanps.groovy.data.model.CastCrewResponseModel
import com.amanps.groovy.data.model.DiscoverApiResponse
import com.amanps.groovy.data.model.Program
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

    @GET("{programType}/{id}?language=en-US")
    fun getProgramDetails(
            @Path("programType") programType: String,
            @Path("id") id: Int,
            @Query("api_key") api_key: String
    ) : Single<Program>

    @GET("{programType}/{id}/credits?language=en-US")
    fun getCastCrewForProgram(
            @Path("programType") programType: String,
            @Path("id") id: Int,
            @Query("api_key") apiKey: String
    ) : Single<CastCrewResponseModel>

    @GET("{programType}/{id}/recommendations?language=en-US")
    fun getRecommendationsForProgram(
            @Path("programType") programType: String,
            @Path("id") id: Int,
            @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

    @GET("{programType}/{id}/similar?language=en-US")
    fun getSimilarPrograms(
            @Path("programType") programType: String,
            @Path("id") id: Int,
            @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

    @GET("movie/upcoming?language=en-US&region=US")
    fun getUpcomingMovies(
            @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

    @GET("movie/now_playing?language=en-US&region=US")
    fun getNowPlayingMovies(
            @Query("api_key") apiKey: String
    ) : Single<DiscoverApiResponse>

    @GET("trending/{media_type}/{time_window}")
    fun getTrendingMediaType(
            @Path("media_type") mediaType: String,
            @Path("time_window") timeWindow: String = "week",
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