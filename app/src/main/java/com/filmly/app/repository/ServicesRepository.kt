package com.filmly.app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.filmly.app.data.model.*
import com.filmly.app.database.FilmlyDatabase
import com.filmly.app.database.asActorDomain
import com.filmly.app.database.asFilmDomain
import com.filmly.app.database.asSerieDomain
import com.filmly.app.network.ActorsPagingSource
import com.filmly.app.network.MoviesPagingSource
import com.filmly.app.network.TVPagingSource
import com.filmly.app.network.TmdbApiteste
import com.filmly.app.ui.cardDetail.*
import com.filmly.app.ui.home.HomeActorNetwork
import com.filmly.app.ui.home.HomeFilmNetwork
import com.filmly.app.ui.home.HomeSerieNetwork
import com.filmly.app.ui.home.Trending
import com.filmly.app.ui.search.MovieResults
import com.filmly.app.ui.search.PersonResults
import com.filmly.app.ui.search.TvResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
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

    suspend fun deleteFavoriteFilm(film: Film) {
        withContext(Dispatchers.IO) {
            film.id?.let { database.FilmlyDatabaseDao.deleteFilm(it) }
            updateFavoriteFilms()
        }
    }

    suspend fun deleteFavoriteSerie( serie: Serie) {
        withContext(Dispatchers.IO) {
            serie.id?.let { database.FilmlyDatabaseDao.deleteSerie(it) }
            updateFavoriteSeries()
        }
    }

    suspend fun deleteFavoriteActor(actor: Actor) {
        withContext(Dispatchers.IO) {
            actor.id?.let { database.FilmlyDatabaseDao.deleteActor(it) }
            updateFavoriteActors()
        }
    }
    
    //Retrofit2 calls

    suspend fun getTrending(type: String): Flow<Trending> {
        withContext(Dispatchers.IO) {
            retrofitService.getTrending(type, StatesRepository.searchTime, "0d3ca7edae2d9cb14c86ce991530aee6").results.asFlow()
        }.let { return it }
    }

    fun getAllPopularMovies(): Flow<PagingData<HomeFilmNetwork>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(retrofitService) }
        ).flow
    }

    fun getAllPopularSeries(): Flow<PagingData<HomeSerieNetwork>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TVPagingSource(retrofitService) }
        ).flow
    }

    fun getAllPopularActors(): Flow<PagingData<HomeActorNetwork>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ActorsPagingSource(retrofitService) }
        ).flow
    }

    fun getMoviesRecommended(movie_id: Int): Flow<PagingData<HomeFilmNetwork>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(retrofitService, movie_id)}
        ).flow
    }

    fun getTvRecommended(tv_id: Int): Flow<PagingData<HomeSerieNetwork>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TVPagingSource(retrofitService, tv_id) }
        ).flow
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

    // Checking if a card is contained in one list
    fun checkCardIsFavorited(card: Card): Boolean? {

        return when (card.type) {
            "movie" -> favoriteFilms.value?.contains(card)
            "tv" -> favoriteSeries.value?.contains(card)
            "person" -> favoriteActors.value?.contains(card)
            else -> false
        }
    }

    suspend fun getTvWatchProvidersModel(id: Int): TvDetailsResults{
        return retrofitService.getTvDetail(id, "0d3ca7edae2d9cb14c86ce991530aee6")
    }

    suspend fun getMovieWatchProvidersModel(id: Int): MovieDetailsResults {
        return retrofitService.getMovieDetail(id, "0d3ca7edae2d9cb14c86ce991530aee6")

    }

    suspend fun getEpisodesModel(id: Int, season_number: Int): TvEpisodesResult {
        return retrofitService.getEpisodeDetail(id, season_number, "0d3ca7edae2d9cb14c86ce991530aee6")

    }

    suspend fun getActorDetail(id: Int): ActorDetail {
        return retrofitService.getActorDetail(id)
    }

    suspend fun getTvCast(id: Int): TvCast = retrofitService.getTvCast(id, "0d3ca7edae2d9cb14c86ce991530aee6")

    suspend fun getMovieCast(id: Int): MovieCast = retrofitService.getMovieCast(id, "0d3ca7edae2d9cb14c86ce991530aee6")

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