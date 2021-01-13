package com.example.filmly.ui.cardDetail

import kotlinx.serialization.Serializable

@Serializable
data class TvDetailsResults(
    var number_of_episode: Int? = null,
    var number_of_seasons: Int? = null,
)

@Serializable
data class TvSeasonResults(
    var poster_path: String? = null,
    var season_number: Int? = null
)