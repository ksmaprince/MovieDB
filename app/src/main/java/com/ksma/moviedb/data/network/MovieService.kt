package com.ksma.moviedb.data.network

import com.ksma.moviedb.data.model.PopularMovie
import com.ksma.moviedb.data.model.UpComingMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(URL.POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int?
    ): Response<PopularMovie>

    @GET(URL.UPCOMING_MOVIES)
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int?
    ): Response<UpComingMovie>

}