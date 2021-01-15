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

    fun isFavorited(detail: CardDetail) {
        viewModelScope.launch {
            isFavorited.postValue(repository.isFavorited(detail))
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

    fun addCard(detail: CardDetail) {
        when (detail.cardInfo) {
            CardDetail.FILM -> insertFilm(detail.card as Film)
            CardDetail.SERIE -> insertSerie(detail.card as Serie)
            CardDetail.ACTOR -> insertActor(detail.card as Actor)
            CardDetail.TRENDING -> {
                when (detail.card) {
                    is Film -> insertFilm(detail.card)
                    is Serie -> insertSerie(detail.card)
                    is Actor -> insertActor(detail.card)
                }
            }
        }
    }

    fun deleteCard(detail: CardDetail) {
        when (detail.cardInfo) {
            CardDetail.FILM -> deleteFilm(detail.card as Film)
            CardDetail.SERIE -> deleteSerie(detail.card as Serie)
            CardDetail.ACTOR -> deleteActor(detail.card as Actor)
            CardDetail.TRENDING -> {
                when (detail.card) {
                    is Film -> deleteFilm(detail.card)
                    is Serie -> deleteSerie(detail.card)
                    is Actor -> deleteActor(detail.card)
                }
            }
        }
    }
}