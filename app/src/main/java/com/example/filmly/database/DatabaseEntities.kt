package com.example.filmly.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_films")
data class Film(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val image: String?,
    val description: String?

)

@Entity(tableName = "favorite_series")
data class Serie(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val image: String?,
    val description: String?
)

@Entity(tableName = "favorite_actors")
data class Actor(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val image: String?,
    val description: String?
)

@Entity(tableName = "watcheds")
data class Watched(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val image: String?,
    val description: String?
)