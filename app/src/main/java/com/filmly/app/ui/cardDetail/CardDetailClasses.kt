package com.filmly.app.ui.cardDetail

import com.filmly.app.ui.home.HomeActorNetwork
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvDetailsResults(
    @SerialName("watch/providers") val watch: TvWatchProvidersResults? = null,
    val seasons: List<TvSeasonResults>? = null,
    val name: String? = null,
    val overview: String? = null,
)

@Serializable
data class MovieDetailsResults(
    @SerialName("watch/providers") val watch: TvWatchProvidersResults? = null,
    val title: String? = null,
    val overview: String? = null
)


@Serializable
data class TvSeasonResults(
    val air_date: String? = null,
    val episode_count: Int? = null,
    val id: Int? = null,
    val name: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val season_number: Int? = null,
)

@Serializable
data class TvEpisodesResult(
    val _id: String? = null,
    val air_date: String? = null,
    val season_number: Int? = null,
    val episodes: List<TvEpisodes>? = null
)

@Serializable
data class TvEpisodes(
    val air_date: String? = null,
    val name: String? = null,
    val episode_number: Int? = null,
    val id: Int? = null,
    val vote_average: Double? = null,
    val still_path: String? = null,
    val overview: String? = null,
    val season_number: Int? = null,
    var watched: Boolean? = false
)


@Serializable
data class TvWatchProvidersResults(val results: TvProvidersCountries? = null)

@Serializable
data class TvProvidersCountries(
    val BR: TvProviders? = null
)

@Serializable
data class TvProviders(
    val link: String? = null,
    val flatrate: List<Provider>? = emptyList(),
    val buy: List<Provider>? = emptyList(),
    val rent: List<Provider>? = emptyList(),
    val ads: List<Provider>? = emptyList()
)

@Serializable
data class Provider(
    val display_priority: Int? = null,
    val logo_path: String? = null,
    val provider_id: Int? = null,
    val provider_name: String? = null
)

@Serializable
data class ActorDetail(
    val id: Int,
    val name: String?,
    val biography: String?
)

@Serializable
data class MovieCast(
    val id: Int,
    val credits: Credits? = null
)

@Serializable
data class TvCast(
    val id: Int,
    val credits: Credits? = null
)

@Serializable
data class Credits(
    val cast: List<HomeActorNetwork>
)