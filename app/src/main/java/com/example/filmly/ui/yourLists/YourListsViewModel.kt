package com.example.filmly.ui.yourLists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.ServicesRepository
import kotlinx.coroutines.launch

class YourListsViewModel(val repository: ServicesRepository) : ViewModel() {
    val favoriteFilms = repository.favoriteFilms
    val favoriteSeries = repository.favoriteSeries
    val favoriteActors = repository.favoriteActors

    private val _headLists = MutableLiveData<List<HeadLists?>>()
    val headLists: LiveData<List<HeadLists?>>
        get() = _headLists

    fun refreshLists() {
        viewModelScope.launch {
            repository.updateFavoriteFilms()
            repository.updateFavoriteSeries()
            repository.updateFavoriteActors()
        }
    }

    fun refreshHeadLists() {
        listOf(
            favoriteFilms.value?.let { HeadLists("Filmes favoritos", it, CardDetail.FILM) },
            favoriteSeries.value?.let { HeadLists("Séries favoritas", it, CardDetail.SERIE) },
            favoriteActors.value?.let { HeadLists("Atores favoritos", it, CardDetail.ACTOR) }
        ).let {
            _headLists.value = it
        }
    }

}