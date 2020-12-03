package com.example.filmly.ui.home

import kotlinx.serialization.Serializable

@Serializable
data class TrendingResults(val page: Int?, val results: List<Trending>?, val status_message: String?, val success: Boolean?, val status_code: Int?)


@Serializable
data class Trending(
    val poster_path: String?,
    val overview: String?,
    val media_type: String?,
    val id: Int?,
    val release_date: String?,
    val name: String?,
    val popularity: Double?,
    val genre_ids: List<Int>?,
    val vote_average: Int?
)
