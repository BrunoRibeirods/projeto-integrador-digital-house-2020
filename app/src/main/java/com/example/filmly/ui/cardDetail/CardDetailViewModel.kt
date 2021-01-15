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
import kotlinx.coroutines.launch

class CardDetailViewModel(private val repository: ServicesRepository): ViewModel() {

    private val _tvSeasonLive = MutableLiveData<List<TvSeasonResults>>()
    val tvSeasonLive: LiveData<List<TvSeasonResults>>
        get() = _tvSeasonLive

    private val _tvProvidersLive = MutableLiveData<TvWatchProvidersResults>()
    val tvProvidersLive: LiveData<TvWatchProvidersResults>
        get() = _tvProvidersLive

    private val _movieProvidersLive = MutableLiveData<TvWatchProvidersResults>()
    val movieProvidersLive: LiveData<TvWatchProvidersResults>
        get() = _movieProvidersLive


    fun getSeasonsDetail(id: Int){
        try {
            viewModelScope.launch {
                _tvSeasonLive.value = repository.getTvSeasonModel(id).seasons
            }
        }catch (e: Exception){
            Log.e("CardDetailViewModel", e.toString())
        }
    }

    fun getProvidersDetail(id: Int){
        try {
            viewModelScope.launch {
                _tvProvidersLive.value = repository.getTvWatchProvidersModel(id).watch
            }
        }catch (e: Exception){
            Log.e("CardDetailViewModel", e.toString())
        }
    }

    fun getProvidersMovieDetail(id: Int){
        try {
            viewModelScope.launch {
                _movieProvidersLive.value = repository.getMovieWatchProvidersModel(id).watch
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