package com.ksma.moviedb.data.network

import com.ksma.moviedb.utils.Resource
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return Resource.Success(body)
                }
            }
            return Resource.Error(null, "${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return Resource.Error(null, e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): Resource<T> =
        Resource.Error(null, "Api call failed: $errorMessage")
}