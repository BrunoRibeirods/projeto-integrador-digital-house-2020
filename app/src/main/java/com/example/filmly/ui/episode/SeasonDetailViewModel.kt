package com.example.filmly.ui.episode

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.ui.cardDetail.TvEpisodesResult
import kotlinx.coroutines.launch

class SeasonDetailViewModel(val repository: ServicesRepository): ViewModel() {

    private val _tvEpisodesLive = MutableLiveData<TvEpisodesResult>()
    val tvEpisodesLive: LiveData<TvEpisodesResult>
        get() = _tvEpisodesLive

    fun getEpisodeTvDetail(id: Int, season_number: Int){
        try {
            viewModelScope.launch {
                _tvEpisodesLive.value = repository.getEpisodesModel(id, season_number)
            }
        }catch (e: Exception){
            Log.e("CardDetailViewModel", e.toString())
        }
    }


}