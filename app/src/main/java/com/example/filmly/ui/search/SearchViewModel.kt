package com.example.filmly.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.network.TmdbApi
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel(val repository: TmdbApi): ViewModel() {
    val moviesLive = MutableLiveData<MovieResults>()
    val tvLive = MutableLiveData<TvResults>()
    val personLive = MutableLiveData<PersonResults>()

    fun getMoviesModel(query: String){
        try {
            viewModelScope.launch {
                moviesLive.value = repository.getSearchMovie("0d3ca7edae2d9cb14c86ce991530aee6", 1, query)
            }
        }catch (e: Exception){
            Log.e("SearchViewModel", e.toString())
        }
    }

    fun getTvModel(query: String){
        try {
            viewModelScope.launch {
                tvLive.value = repository.getSearchTv("0d3ca7edae2d9cb14c86ce991530aee6", 1, query)
            }
        }catch (e: Exception){
            Log.e("SearchViewModel", e.toString())
        }
    }

    fun getPersonModel(query: String){
        try {
            viewModelScope.launch {
                personLive.value = repository.getSearchPerson("0d3ca7edae2d9cb14c86ce991530aee6", 1, query)
            }
        }catch (e: Exception){
            Log.e("SearchViewModel", e.toString())
        }
    }


}