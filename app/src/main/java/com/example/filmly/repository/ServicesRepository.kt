package com.example.filmly.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filmly.data.model.Actor
import com.example.filmly.data.model.Film
import com.example.filmly.data.model.Serie
import com.example.filmly.data.model.Watchable
import com.example.filmly.database.FilmlyDatabase
import com.example.filmly.database.asActorDomain
import com.example.filmly.database.asFilmDomain
import com.example.filmly.database.asSerieDomain
import com.example.filmly.network.TmdbApiteste
import com.example.filmly.ui.cardDetail.*
import com.example.filmly.ui.home.TrendingResults
import com.example.filmly.ui.search.MovieResults
import com.example.filmly.ui.search.PersonResults
import com.example.filmly.ui.search.TvResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class ServicesRepository {
    abstract val database: FilmlyDatabase
    private val retrofitService = TmdbApiteste.retrofitService

    private val _favoriteFilms = MutableLiveData<List<Film>>()
    val favoriteFilms: LiveData<List<Film>>
        get() = _favoriteFilms

    private val _favoriteSeries = MutableLiveData<List<Serie>>()
    val favoriteSeries: LiveData<List<Serie>>
        get() = _favoriteSeries

    private val _favoriteActors = MutableLiveData<List<Actor>>()
    val favoriteActors: LiveData<List<Actor>>
        get() = _favoriteActors

    //Room calls
    suspend fun insertFilm(film: Film) {
        database.FilmlyDatabaseDao.insert(film.asFilmDatabase())
    }

    suspend fun insertSerie(serie: Serie) {
        database.FilmlyDatabaseDao.insert(serie.asSerieDatabase())
    }

    suspend fun insertActor(actor: Actor) {
        database.FilmlyDatabaseDao.insert(actor.asActorDatabase())
    }

    suspend fun insertWatched(watchable: Watchable) {
        database.FilmlyDatabaseDao.insert(watchable.asWatched())
    }

    suspend fun updateFavoriteFilms() {
        withContext(Dispatchers.IO) {
            _favoriteFilms.postValue(database.FilmlyDatabaseDao.getFavoriteFilms().asFilmDomain())
        }
    }

    suspend fun updateFavoriteSeries() {
        withContext(Dispatchers.IO) {
            _favoriteSeries.postValue(database.FilmlyDatabaseDao.getFavoriteSeries().asSerieDomain())
        }
    }

    suspend fun updateFavoriteActors() {
        withContext(Dispatchers.IO) {
            _favoriteActors.postValue(database.FilmlyDatabaseDao.getFavoriteActors().asActorDomain())
        }
    }
    
    //Retrofit2 calls

    suspend fun getTrending(type: String): TrendingResults {
        return retrofitService.getTrending(type, StatesRepository.searchTime, "0d3ca7edae2d9cb14c86ce991530aee6")
    }

    suspend fun getMoviesModel(query: String): MovieResults {
        return retrofitService.getSearchMovie("0d3ca7edae2d9cb14c86ce991530aee6", 1, query)
    }

    suspend fun getTvModel(query: String): TvResults {
        return retrofitService.getSearchTv("0d3ca7edae2d9cb14c86ce991530aee6", 1, query)
    }

    suspend fun getPersonModel(query: String): PersonResults {
        return retrofitService.getSearchPerson("0d3ca7edae2d9cb14c86ce991530aee6", 1, query)
    }



    suspend fun getTvWatchProvidersModel(id: Int): TvDetailsResults{
        return retrofitService.getTvDetail(id, "0d3ca7edae2d9cb14c86ce991530aee6")
    }

    suspend fun getMovieWatchProvidersModel(id: Int): MovieDetailsResults {
        return retrofitService.getMovieDetail(id, "0d3ca7edae2d9cb14c86ce991530aee6")
    }

    //Singleton
    companion object {
        private var INSTANCE: ServicesRepository? = null

        fun getInstance(context: Context): ServicesRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = object : ServicesRepository() {
                        override val database: FilmlyDatabase
                            get() = FilmlyDatabase.getInstance(context)
                    }
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}