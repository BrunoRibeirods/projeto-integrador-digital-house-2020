package com.example.filmly.ui.home

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.data.model.HeadLists
import com.example.filmly.data.model.UserInformation
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.repository.StatesRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: ServicesRepository, val statesRepository: StatesRepository): ViewModel() {
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