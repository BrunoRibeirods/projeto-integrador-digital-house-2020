package com.example.filmly.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilmlyDao {
    @Insert
    suspend fun insert(film: Film)

    @Insert
    suspend fun insert(serie: Serie)

    @Insert
    suspend fun insert(actor: Actor)

    @Query("SELECT * FROM favorite_films ORDER BY id DESC")
    suspend fun getFavoriteFilms(): LiveData<List<Film>>

    @Query("SELECT * FROM favorite_series ORDER BY id DESC")
    suspend fun getFavoriteSeries(): LiveData<List<Serie>>

    @Query("SELECT * FROM favorite_actors ORDER BY id DESC")
    suspend fun getFavoriteActors(): LiveData<List<Actor>>
}