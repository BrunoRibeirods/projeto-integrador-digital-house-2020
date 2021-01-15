package com.example.filmly.database

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

    @Insert
    suspend fun insert(watched: Watched)

    @Query("SELECT * FROM favorite_films")
    suspend fun getFavoriteFilms(): List<Film>

    @Query("SELECT * FROM favorite_series")
    suspend fun getFavoriteSeries(): List<Serie>

    @Query("SELECT * FROM favorite_actors")
    suspend fun getFavoriteActors(): List<Actor>

    @Query("SELECT * FROM watcheds")
    suspend fun getWatcheds(): List<Watched>

    @Query("DELETE FROM favorite_films WHERE marvel_id = :id")
    fun deleteFilm(id: Int)

    @Query("DELETE FROM favorite_series WHERE marvel_id = :id")
    fun deleteSerie(id: Int)

    @Query("DELETE FROM favorite_actors WHERE marvel_id = :id")
    fun deleteActor(id: Int)
}