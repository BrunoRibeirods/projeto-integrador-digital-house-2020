package com.example.filmly.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.filmly.data.model.Card
import com.example.filmly.repository.ServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(val repository: ServicesRepository) : ViewModel() {

    fun getTrending(): MutableLiveData<MutableList<Card>> {
        val mutableList = mutableListOf<Card>()
        val mutableLiveData = MutableLiveData<MutableList<Card>>()
        viewModelScope.launch {
            repository.getTrending("all").collect {
                when (it.media_type) {
                    "movie" -> it.convertToCard()
                    "tv" -> it.convertToTv()
                    else -> it.convertToPerson()
                }.let { mutableList.add(it) }
            }
            mutableLiveData.postValue(mutableList)
        }

        return mutableLiveData
    }

    fun getAllPopularMovies(): Flow<PagingData<PopularMovie>> =
        repository.getAllPopularMovies().cachedIn(viewModelScope)

    fun getAllPopularSeries(): Flow<PagingData<PopularTV>> =
        repository.getAllPopularSeries().cachedIn(viewModelScope)

    fun getAllPopularActors(): Flow<PagingData<PopularActor>> =
        repository.getAllPopularActors().cachedIn(viewModelScope)

    fun refreshLists() {
        viewModelScope.launch {
            repository.updateFavoriteFilms()
            repository.updateFavoriteSeries()
            repository.updateFavoriteActors()
        }
    }


}