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


    private val _tvProvidersLive = MutableLiveData<TvDetailsResults>()
    val tvProvidersLive: LiveData<TvDetailsResults>
        get() = _tvProvidersLive

    private val _movieProvidersLive = MutableLiveData<MovieDetailsResults>()
    val movieProvidersLive: LiveData<MovieDetailsResults>
        get() = _movieProvidersLive



    fun getProvidersDetail(id: Int){
        try {
            viewModelScope.launch {
                _tvProvidersLive.value = repository.getTvWatchProvidersModel(id)
            }
        }catch (e: Exception){
            Log.e("CardDetailViewModel", e.toString())
        }
    }

    fun getProvidersMovieDetail(id: Int){
        try {
            viewModelScope.launch {
                _movieProvidersLive.value = repository.getMovieWatchProvidersModel(id)
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