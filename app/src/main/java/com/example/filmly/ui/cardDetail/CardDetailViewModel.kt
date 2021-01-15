package com.example.filmly.ui.cardDetail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.data.model.*
import com.example.filmly.repository.ServicesRepository
import kotlinx.coroutines.launch

class CardDetailViewModel(private val repository: ServicesRepository) : ViewModel() {
    val isFavorited = MutableLiveData<Boolean>()


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

    fun isFavorited(card: Card) {
        viewModelScope.launch {
            isFavorited.postValue(repository.isFavorited(card))
        }
    }

    suspend fun insertWatched(watchable: Watchable) {
        viewModelScope.launch {
            repository.insertWatched(watchable)
        }
    }

    fun deleteFilm(film: Film) {
        viewModelScope.launch {
            repository.deleteFavoriteFilm(film)
        }
    }

    fun deleteSerie(serie: Serie) {
        viewModelScope.launch {
            repository.deleteFavoriteSerie(serie)
        }
    }

    fun deleteActor(actor: Actor) {
        viewModelScope.launch {
            repository.deleteFavoriteActor(actor)
        }
    }

    fun addCard(card: Card) {
        when (card) {
            is Film -> insertFilm(card)
            is Serie -> insertSerie(card)
            is Actor -> insertActor(card)
        }
    }

    fun deleteCard(card: Card) {
        when (card) {
            is Film -> deleteFilm(card)
            is Serie -> deleteSerie(card)
            is Actor -> deleteActor(card)
        }
    }
}