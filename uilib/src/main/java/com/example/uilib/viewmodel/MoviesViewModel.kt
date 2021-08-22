package com.example.uilib.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.bl.repository.MovieRepository
import com.example.bl.repository.Repository
import com.example.uilib.models.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String = MoviesViewModel::class.java.simpleName
    private val movieList: MediatorLiveData<List<Movie>>
    private val movieSelectItem :MediatorLiveData<Movie>
    //private val callbackResEvent = CallbackResponseEvent()

    private var repository: Repository

    init {
        movieList = MediatorLiveData()
        movieSelectItem = MediatorLiveData()
        repository = MovieRepository.getInstance(application)
        viewModelScope.launch {
            repository.getMoviesItem().collect {
                Log.d(TAG,"Collect movie list")
                val list =  it.map { item -> Movie(item.title, item.content, item.poster,
                                                item.rate, item.date) }.toList()
                notifyUpdateData(list)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.closeRepository()
        Log.d(TAG, "onCleared")
    }

    fun fetchAllMovies() {
        Log.d(TAG, "fetchAllMovies")
        repository.requestList()
    }

    fun getMovieList(): LiveData<List<Movie>> {
        return movieList
    }


    fun getMovieSelect(): LiveData<Movie> {
        return movieSelectItem
    }

    fun onClickItem(movieData: Movie) {
        Log.d(TAG, "onClickItem: $movieData")
        movieSelectItem.postValue(movieData)
    }


    private fun notifyUpdateData(movies: List<Movie>) {
        if (!movies.isEmpty()) {
            movieList.postValue(movies)
        }
    }

//   private inner class CallbackResponseEvent: ResponseEvent {
//       override fun <T> onResponse(response: T, codeStatus: Int) {
//           // call from main thread
//           Log.d(TAG, "status: $codeStatus")
//           if (codeStatus == 200) {
//               val res: ResponseData = response as ResponseData
//               //notifyUpdateData(res.result)
//           } else {
//               Log.d(TAG, "failed to request movies")
//           }
//       }
//   }
}