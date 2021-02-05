package com.example.filmly.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.filmly.data.model.Card
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.repository.StatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.filmly.data.model.UserInformation

class HomeViewModel(val repository: ServicesRepository, val statesRepository: StatesRepository): ViewModel() {

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

    fun trendingMessage(): String {
        return when(StatesRepository.searchTime) {
            "day" -> "Trending do Dia"
            else -> "Trending da Semana"
        }
    }

    fun getAllPopularMovies(): Flow<PagingData<HomeFilmNetwork>> =
        repository.getAllPopularMovies().cachedIn(viewModelScope)

    fun getAllPopularSeries(): Flow<PagingData<HomeSerieNetwork>> =
        repository.getAllPopularSeries().cachedIn(viewModelScope)

    fun getAllPopularActors(): Flow<PagingData<HomeActorNetwork>> =
        repository.getAllPopularActors().cachedIn(viewModelScope)

    fun refreshLists() {
        viewModelScope.launch {
            repository.updateFavoriteFilms()
            repository.updateFavoriteSeries()
            repository.updateFavoriteActors()
        }
    }

    fun saveInformation(user: UserInformation) {
        statesRepository.updateUserInformation(user)
        StatesRepository.updateUserInformation(user)
    }





}