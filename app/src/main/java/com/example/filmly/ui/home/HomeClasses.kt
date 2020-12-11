package com.example.filmly.ui.home

import com.example.filmly.data.model.Actor
import com.example.filmly.data.model.Film
import com.example.filmly.data.model.Serie
import com.example.filmly.ui.search.KnownFor
import kotlinx.serialization.Serializable

@Serializable
data class TrendingResults(val page: Int?, val results: List<Trending>)


@Serializable
data class Trending(
    val poster_path: String? = null,
    val overview: String? = null,
    val media_type: String? = null,
    val id: Int?,
    val profile_path: String? = null,
    val known_for: List<KnownFor>? = null,
    val name: String? = null,
    val release_date: String? = "",
    val title: String?="",
    val popularity: Double? = null,
    val genre_ids: List<Int>? = null,
    val vote_average: Double? = null
){
    fun convertToCard(): Film {
        return Film(id = id, name = title, image = poster_path, descricao = overview)
    }

    fun convertToTv(): Serie {
        return Serie(id = id, name = name, image = poster_path, descricao = overview)
    }

    fun convertToPerson(): Actor {
        val over = known_for?.map { it.title }
        return Actor(id = id, name = name, image = profile_path, descricao =  over?.joinToString(prefix = "Conhecido por:  \n", separator = "\n"))
    }
}