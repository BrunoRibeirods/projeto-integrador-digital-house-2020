package com.example.filmly.ui.home

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.ServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(val repository: ServicesRepository): ViewModel() {
    private val _trendingLive = MutableLiveData<TrendingResults>()
    val trendingLive: LiveData<TrendingResults>
        get() = _trendingLive

    val headLists = mutableListOf<HeadLists>()

    fun getTrendingLive(type: String){
        viewModelScope.launch {
            try {
                _trendingLive.value = repository.getTrending(type)

            }catch (e:Exception){
                Log.e("HomeViewModel", e.toString())
            }
        }
    }

    fun getAllPopularMovies(): Flow<PagingData<PopularMovie>> = repository.getAllPopularMovies().cachedIn(viewModelScope)

    fun refreshLists() {
        viewModelScope.launch {
            repository.updateFavoriteFilms()
            repository.updateFavoriteSeries()
            repository.updateFavoriteActors()
        }
    }







}