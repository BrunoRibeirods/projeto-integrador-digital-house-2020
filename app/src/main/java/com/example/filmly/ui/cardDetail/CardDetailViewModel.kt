package com.example.filmly.ui.cardDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.data.model.Actor
import com.example.filmly.data.model.Film
import com.example.filmly.data.model.Serie
import com.example.filmly.data.model.Watchable
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.ui.search.MovieResults
import kotlinx.coroutines.launch

class CardDetailViewModel(private val repository: ServicesRepository): ViewModel() {
    private val _tvDetailsLive = MutableLiveData<TvDetailsResults>()
    val tvDetailsLive: LiveData<TvDetailsResults>
        get() = _tvDetailsLive

    private val _tvSeasonLive = MutableLiveData<TvSeasonResults>()
    val tvSeasonLive: LiveData<TvSeasonResults>
        get() = _tvSeasonLive

    fun getSeasonsNumbers(id: Int){
        try {
            viewModelScope.launch {
                _tvDetailsLive.value = repository.getTvDetailsModel(id)
            }
        }catch (e: Exception){
            Log.e("CardDetailViewModel", e.toString())
        }
    }

    fun getSeasonsDetail(id: Int, seasonNumber: Int){
        try {
            viewModelScope.launch {
                _tvSeasonLive.value = repository.getTvSeasonModel(id, seasonNumber)
            }
        }catch (e: Exception){
            Log.e("CardDetailViewModel", e.toString())
        }
    }


    fun insertFilm(film: Film) {
        viewModelScope.launch {
            repository.insertFilm(film)
        }
    }

    fun insertSerie(serie: Serie) {
        viewModelScope.launch {
            repository.insertSerie(serie)
        }
    }

    fun insertActor(actor: Actor) {
        viewModelScope.launch {
            repository.insertActor(actor)
        }
    }

    suspend fun insertWatched(watchable: Watchable) {
        viewModelScope.launch {
            repository.insertWatched(watchable)
        }
    }
}