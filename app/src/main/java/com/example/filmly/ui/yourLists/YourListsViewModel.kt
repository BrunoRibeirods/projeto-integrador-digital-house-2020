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
        val list = mutableListOf<HeadLists>()

        if (favoriteFilms.value?.isNotEmpty() == true){
            list.add(favoriteFilms.value!!.let { HeadLists("Filmes favoritos", it, CardDetail.FILM) })
        }
        if (favoriteSeries.value?.isNotEmpty() == true){
            list.add(favoriteSeries.value!!.let { HeadLists("Séries favoritas", it, CardDetail.SERIE) })
        }
        if (favoriteActors.value?.isNotEmpty() == true){
            list.add(favoriteActors.value!!.let { HeadLists("Atores favoritos", it, CardDetail.ACTOR) })
        }
        list.let { _headLists.value = it }

    }

}