package com.filmly.app.network

import com.filmly.app.ui.cardDetail.*
import com.filmly.app.ui.home.PopularActorsResults
import com.filmly.app.ui.home.PopularMoviesResults
import com.filmly.app.ui.home.PopularTVResults
import com.filmly.app.ui.home.TrendingResults
import com.filmly.app.ui.search.MovieResults
import com.filmly.app.ui.search.PersonResults
import com.filmly.app.ui.search.TvResults
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("trending/{type}/{time}?language=pt-BR")
    suspend fun getTrending(
        @Path("type") type: String,
        @Path("time") time: String,
        @Query("api_key") api_key: String
    ): TrendingResults

    //Incremented
    @GET("tv/popular?language=pt-BR&api_key=0d3ca7edae2d9cb14c86ce991530aee6")
    suspend fun getAllPopularTV(
        @Query("page") page: Int
    ): PopularTVResults

    @GET("movie/popular?language=pt-BR&api_key=0d3ca7edae2d9cb14c86ce991530aee6")
    suspend fun getAllPopularMovies(
        @Query("page") page: Int
    ): PopularMoviesResults

    @GET("person/popular?language=pt-BR&api_key=0d3ca7edae2d9cb14c86ce991530aee6")
    suspend fun getAllPopularActors(
        @Query("page") page: Int
    ): PopularActorsResults
    //

    @GET("search/movie?language=pt-BR")
    suspend fun getSearchMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): MovieResults

    @GET("search/tv?language=pt-BR")
    suspend fun getSearchTv(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): TvResults

    @GET("search/person?language=pt-BR")
    suspend fun getSearchPerson(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): PersonResults

    @GET("tv/{tv_id}?language=pt-BR&append_to_response=watch/providers,season")
    suspend fun getTvDetail(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String
    ): TvDetailsResults

    @GET("movie/{movie_id}?language=pt-BR&append_to_response=watch/providers")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): MovieDetailsResults

    @GET("tv/{tv_id}/season/{season_number}?language=pt-BR")
    suspend fun getEpisodeDetail(
        @Path("tv_id") tv_id: Int,
        @Path("season_number") season_number: Int,
        @Query("api_key") api_key: String
    ): TvEpisodesResult

    @GET("person/{person_id}?language=pt-BR&api_key=0d3ca7edae2d9cb14c86ce991530aee6&append_to_response=popular")
    suspend fun getActorDetail(
        @Path("person_id") person_id: Int
    ): ActorDetail

    @GET("tv/{tv_id}/recommendations?language=pt-BR")
    suspend fun getTvRecommendations(
        @Path("tv_id") tv_id: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): PopularTVResults

    @GET("movie/{movie_id}/recommendations?language=pt-BR")
    suspend fun getMovieRecommendations(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): PopularMoviesResults

    @GET("movie/{movie_id}?language=pt-BR&append_to_response=credits")
    suspend fun getMovieCast(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): MovieCast

    @GET("tv/{tv_id}?language=pt-BR&append_to_response=credits")
    suspend fun getTvCast(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String
    ): TvCast

}



object TmdbApiteste{
    val retrofitService: TmdbApi by lazy {
        retrofitCreate().create(TmdbApi::class.java)
    }

    val httpClient = OkHttpClient.Builder()

    fun getHttp() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
    }



    fun retrofitCreate(): Retrofit {
        val contentType = "application/json".toMediaType()
        getHttp()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(Json{ignoreUnknownKeys = true; isLenient = true}.asConverterFactory(contentType))
            .client(httpClient.build())
            .build()
    }




}


