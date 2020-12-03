package com.example.filmly.ui.search

import kotlinx.serialization.Serializable

@Serializable
data class MovieResults(val page: Int, val result: List<MovieResults>)

@Serializable
data class Movie(
    val poster_path: String?,
    val overview: String?,
    val id: Int?,
    val title: String?,
    val popularity: Double?,
    val release_date: String?,
    val genre_ids: List<Int>?,
    val vote_average: Int?
)

@Serializable
data class TvResults(val page: Int, val result: List<TvResults>)

@Serializable
data class TvShow(
    val poster_path: String?,
    val overview: String?,
    val id: Int?,
    val name: String?,
    val first_air_date: String?,
    val popularity: Double?,
    val genre_ids: List<Int>?,
    val vote_average: Int?
)


@Serializable
data class PersonResults(val page: Int, val result: List<PersonResults>)

@Serializable
data class Person(
    val profile_path: String?,
    val id: Int?,
    val known_for: List<KnownFor>,
    val name: String?,
    val popularity: Double?
)

@Serializable
data class KnownFor(
    val poster_path: String?,
    val overview: String?,
    val media_type: String?,
    val id: Int?,
    val name: String?,
    val first_air_date: String?,
    val popularity: Double?,
    val genre_ids: List<Int>?,
    val vote_average: Int?
)


