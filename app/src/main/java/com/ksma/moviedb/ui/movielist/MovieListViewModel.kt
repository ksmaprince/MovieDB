package com.ksma.moviedb.ui.movielist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksma.moviedb.data.model.PopularMovie
import com.ksma.moviedb.data.model.UpComingMovie
import com.ksma.moviedb.repository.MovieDBRepository
import com.ksma.moviedb.utils.Resource
import com.ksma.moviedb.utils.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieDBRepository, @ApplicationContext private val context: Context
) : ViewModel() {

    private val _moviePopular: MutableLiveData<Resource<PopularMovie>> = MutableLiveData()
    val moviePopular: LiveData<Resource<PopularMovie>> = _moviePopular
    private val _movieUpcoming: MutableLiveData<Resource<UpComingMovie>> = MutableLiveData()
    val upComingMovie: LiveData<Resource<UpComingMovie>> = _movieUpcoming

    //For without coroutine Flow
    val upCommingMovie : MutableLiveData<Resource<UpComingMovie>> = MutableLiveData()

    fun fetchPopularMovie(apikey: String, page: Int) = viewModelScope.launch {
        repository.getPopularMovieList(apikey, page).collect {
            _moviePopular.value = it
        }
    }

    /*fun fetchUpcommingMovie(apikey: String, page: Int) = viewModelScope.launch {
        repository.getUpcommingMovie(apikey, page).collect {
            _movieUpcoming.value = it
        }
    }*/

    //without coroutine flow
    fun fetchUpcommingMovie(apikey: String, page: Int) {
        upCommingMovie.postValue(Resource.Loading(null))
        viewModelScope.launch {
            try {
                if (hasInternetConnection(context)){
                    val result = repository.getUpcommingMovie(apikey, page)
                    upCommingMovie.postValue(Resource.Success(result.body()!!))
                }else{
                    upCommingMovie.postValue(Resource.Error(data = null, message = "No Internet Connection"))
                }
            }catch (e: Exception){
                when (e){
                    is IOException -> upCommingMovie.postValue(Resource.Error(data = null, message = "API Failure ${e.message}"))
                    else -> upCommingMovie.postValue(Resource.Error(data = null, message = "Conversion Error"))
                }
            }
        }
    }
}