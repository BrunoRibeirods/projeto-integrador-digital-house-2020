package com.example.filmly.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.ServicesRepository
import kotlinx.coroutines.launch

class SearchViewModel(val repository: ServicesRepository): ViewModel() {
    private val _moviesLive = MutableLiveData<MovieResults>()
    val moviesLive: LiveData<MovieResults>
        get() = _moviesLive

    private val _tvLive = MutableLiveData<TvResults>()
    val tvLive: LiveData<TvResults>
        get() = _tvLive

    private val _personLive = MutableLiveData<PersonResults>()
    val personLive: LiveData<PersonResults>
        get() = _personLive

    var headLists = mutableListOf<HeadLists>()

    fun updateMoviesLive(query: String){
        try {
            viewModelScope.launch {
                _moviesLive.value = repository.getMoviesModel(query)
            }
        }catch (e: Exception){
            Log.e("SearchViewModel", e.toString())
        }
    }

    fun updateTvLive(query: String){
        try {
            viewModelScope.launch {
                _tvLive.value = repository.getTvModel(query)
            }
        }catch (e: Exception){
            Log.e("SearchViewModel", e.toString())
        }
    }

    fun updatePersonLive(query: String){
        try {
            viewModelScope.launch {
                _personLive.value = repository.getPersonModel(query)
            }
        }catch (e: Exception){
            Log.e("SearchViewModel", e.toString())
        }
    }
}