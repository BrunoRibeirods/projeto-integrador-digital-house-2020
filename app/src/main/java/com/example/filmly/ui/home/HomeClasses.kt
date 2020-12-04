package com.example.filmly.ui.home

import kotlinx.serialization.Serializable

@Serializable
data class TrendingResults(val page: Int?, val results: List<Trending>?)


@Serializable
data class Trending(
    val poster_path: String?,
    val overview: String?,
    val media_type: String?,
    val id: Int?,
    val release_date: String? = "",
    val title: String?="",
    val popularity: Double? = null,
    val genre_ids: List<Int>?,
    val vote_average: Double?
)
