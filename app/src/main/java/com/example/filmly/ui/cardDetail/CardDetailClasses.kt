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



@Serializable
data class TvWatchProvidersResults(val id: Int? = null, val results: TvProvidersCountries? = null)

@Serializable
data class TvProvidersCountries(
    val BR: TvProviders? = null
)

@Serializable
data class TvProviders(
    val link: String? = null,
    val flatrate: List<Provider>? = null
)

@Serializable
data class Provider(
    val display_priority: Int? = null,
    val logo_path: String? = null,
    val provider_id: Int? = null,
    val provider_name: String? = null
)