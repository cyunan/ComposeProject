package com.cyn.composeproject.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyn.composeproject.api.NetWorkService
import com.cyn.composeproject.model.MovieItemModel

class MovieViewModel : BaseViewModel() {
    val moviesLiveData = MutableLiveData<List<MovieItemModel>>()
    fun getMovieLists(){
        launch {
            val movieModel = NetWorkService.getMovies()
            moviesLiveData.value = movieModel.itemList
        }
    }
}