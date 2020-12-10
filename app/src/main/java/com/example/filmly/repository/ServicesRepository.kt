package com.example.filmly.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.filmly.data.model.Actor
import com.example.filmly.data.model.Film
import com.example.filmly.data.model.Serie
import com.example.filmly.data.model.Watchable
import com.example.filmly.database.FilmlyDatabase
import com.example.filmly.database.Watched


abstract class ServicesRepository {
    abstract val database: FilmlyDatabase

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

    suspend fun getFavoriteFilms(): LiveData<List<com.example.filmly.database.Film>> {
        return database.FilmlyDatabaseDao.getFavoriteFilms()
    }

    suspend fun getFavoriteSeries(): LiveData<List<com.example.filmly.database.Serie>> {
        return database.FilmlyDatabaseDao.getFavoriteSeries()
    }

    suspend fun getFavoriteActors(): LiveData<List<com.example.filmly.database.Actor>> {
        return database.FilmlyDatabaseDao.getFavoriteActors()
    }

    suspend fun getWatcheds(): LiveData<List<Watched>> {
        return database.FilmlyDatabaseDao.getWatcheds()
    }

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