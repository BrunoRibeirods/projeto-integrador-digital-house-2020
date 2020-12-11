package com.example.filmly.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.network.TmdbApi
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(val repository: TmdbApi): ViewModel() {
    val trendingLive = MutableLiveData<TrendingResults>()

    fun getTrending(type: String, time: String){
        viewModelScope.launch {
            try {
                trendingLive.value = repository.getTrending(type, time, "0d3ca7edae2d9cb14c86ce991530aee6")
            }catch (e:Exception){
                Log.e("HomeViewModel", e.toString())
            }
        }
    }





}