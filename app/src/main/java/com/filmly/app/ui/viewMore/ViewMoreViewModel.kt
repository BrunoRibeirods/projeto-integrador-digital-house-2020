package com.filmly.app.ui.viewMore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.filmly.app.repository.ServicesRepository
import com.filmly.app.ui.home.HomeActorNetwork
import com.filmly.app.ui.home.HomeFilmNetwork
import com.filmly.app.ui.home.HomeSerieNetwork
import kotlinx.coroutines.flow.Flow

class ViewMoreViewModel(val repository: ServicesRepository) : ViewModel() {

    fun getAllPopularMovies(): Flow<PagingData<HomeFilmNetwork>> =
        repository.getAllPopularMovies().cachedIn(viewModelScope)

    fun getAllPopularSeries(): Flow<PagingData<HomeSerieNetwork>> =
        repository.getAllPopularSeries().cachedIn(viewModelScope)

    fun getAllPopularActors(): Flow<PagingData<HomeActorNetwork>> =
        repository.getAllPopularActors().cachedIn(viewModelScope)
}