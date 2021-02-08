package com.filmly.app.ui.home

import com.filmly.app.data.model.Actor
import com.filmly.app.data.model.Film
import com.filmly.app.data.model.Serie
import com.filmly.app.ui.search.KnownFor
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
    val title: String? = "",
    val popularity: Double? = null,
    val genre_ids: List<Int>? = null,
    val vote_average: Double? = null
) {
    fun convertToCard(): Film {
        return Film(
            id = id,
            name = title,
            image = poster_path,
            descricao = overview,
            type = media_type
        )
    }

    fun convertToTv(): Serie {
        return Serie(
            id = id,
            name = name,
            image = poster_path,
            descricao = overview,
            type = media_type
        )
    }

    fun convertToPerson(): Actor {
        val over = known_for?.map { it.title ?: it.name }
        return Actor(
            id = id,
            name = name,
            image = profile_path,
            descricao = over?.joinToString(prefix = "Conhecido por:  \n", separator = "\n"),
            type = media_type
        )
    }
}

@Serializable
data class PopularTVResults(
    val page: Int?, val results: List<HomeSerieNetwork>
)

@Serializable
data class HomeSerieNetwork(
    val id: Int,
    val name: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val vote_average: Double? = null,
    val genre_ids: List<Int>? = null,
    val popularity: Double? = null
) {

    fun convertToSerie() = com.filmly.app.data.model.Serie(
        id = id,
        name = name,
        image = poster_path,
        descricao = overview,
        type = "tv"
    )
}

@Serializable
data class PopularMoviesResults(val page: Int?, val results: List<HomeFilmNetwork>)

@Serializable
data class HomeFilmNetwork(
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val vote_average: Double? = null,
    val genre_ids: List<Int>? = null,
    val popularity: Double? = null
) {
    fun convertToFilm() = Film(id, title, poster_path, overview, "movie")
}

@Serializable
data class PopularActorsResults(val page: Int?, val results: List<HomeActorNetwork>)

@Serializable
data class HomeActorNetwork(
    val id: Int,
    val name: String? = null,
    val profile_path: String? = null,
    val popularity: Double? = null,
    val known_for: List<KnownFor>? = null
) {

    fun convertToActor(): com.filmly.app.data.model.Actor {
        return com.filmly.app.data.model.Actor(
            id = id,
            name = name,
            image = profile_path,
            descricao = null,
            type = "person",
            known_for = null
        )
    }
}