package com.ksma.moviedb.data.network

import com.ksma.moviedb.utils.Resource
import com.ksma.moviedb.utils.hasInternetConnection
import retrofit2.Response
import java.io.IOException

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        if(hasInternetConnection()){
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
                return when(e){
                    is IOException -> {
                        Resource.Error(null, e.message ?: e.toString())
                    }
                    else -> {
                        Resource.Error(null, "Conversion Error")
                    }
                }

            }
        }else{
            return Resource.Error(null, "No Connection, Please Try Again")
        }

    }
}