package com.example.filmly.ui.search

import kotlinx.serialization.Serializable

@Serializable
data class MovieResults(val page: Int = 1, val results: List<Movie>)

@Serializable
data class Movie(
    val poster_path: String? = null,
    val overview: String? = null,
    val id: Int?,
    val title: String? = null,
    val popularity: Double?,
    val release_date: String? = null,
    val genre_ids: List<Int>?,
    val vote_average: Double?
)

@Serializable
data class TvResults(val page: Int, val results: List<TvShow>)

@Serializable
data class TvShow(
    val poster_path: String? = null,
    val overview: String? = null,
    val id: Int?,
    val name: String? = null,
    val first_air_date: String? = null,
    val popularity: Double?,
    val genre_ids: List<Int>?,
    val vote_average: Double?
)


@Serializable
data class PersonResults(val page: Int, val results: List<Person>)

@Serializable
data class Person(
    val profile_path: String? = null,
    val id: Int?,
    val known_for: List<KnownFor>,
    val name: String? = null,
    val popularity: Double? = null
)

@Serializable
data class KnownFor(
    val poster_path: String? = null,
    val overview: String? = null,
    val media_type: String? = null,
    val id: Int?,
    val name: String? = null,
    val first_air_date: String? = null,
    val popularity: Double? = null,
    val genre_ids: List<Int>?,
    val vote_average: Double?
)


