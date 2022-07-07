package com.ksma.moviedb.repository

import com.ksma.moviedb.data.model.PopularMovie
import com.ksma.moviedb.data.model.UpComingMovie
import com.ksma.moviedb.data.network.BaseApiResponse
import com.ksma.moviedb.data.network.MovieService
import com.ksma.moviedb.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDBRepository @Inject constructor(private val movieService: MovieService) :
    BaseApiResponse() {
    suspend fun getPopularMovieList(apiKey: String, page: Int?): Flow<Resource<PopularMovie>> =
        flow { emit(safeApiCall { movieService.getPopularMovies(apiKey, page) }) }.flowOn(
            Dispatchers.IO
        )

    /*suspend fun getUpcommingMovie(apiKey: String, page: Int?): Flow<Resource<UpComingMovie>> =
        flow { emit(safeApiCall { movieService.getUpComingMovies(apiKey, page) }) }.flowOn(
            Dispatchers.IO
        )*/

    suspend fun getUpcommingMovie(apiKey: String, page: Int?): Response<UpComingMovie> =
        withContext(Dispatchers.IO){
            movieService.getUpComingMovies(apiKey, page)
        }

}