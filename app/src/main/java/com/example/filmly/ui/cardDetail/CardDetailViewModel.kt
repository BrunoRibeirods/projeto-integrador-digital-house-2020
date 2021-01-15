package com.example.filmly.ui.cardDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.data.model.*
import com.example.filmly.repository.ServicesRepository
import kotlinx.coroutines.launch

class CardDetailViewModel(private val repository: ServicesRepository) : ViewModel() {
    val isFavorited = MutableLiveData<Boolean>()

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