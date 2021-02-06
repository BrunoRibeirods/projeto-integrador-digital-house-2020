package com.filmly.app.ui.cardDetail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filmly.app.data.model.*
import com.filmly.app.repository.ServicesRepository
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

    fun checkCardisFavorited(card: Card) {
        viewModelScope.launch {
            isFavorited.postValue(repository.checkCardIsFavorited(card))
        }
    }

    fun isFavorited() {
        isFavorited.value = true
    }

    fun isNotFavorited() {
        isFavorited.value = false
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

    fun getActorDetail(id: Int): MutableLiveData<ActorDetail> {
        val mutableLiveData = MutableLiveData<ActorDetail>()
        viewModelScope.launch {
            mutableLiveData.postValue(repository.getActorDetail(id))
        }
        return mutableLiveData
    }
}