package com.example.filmly.network

import com.example.filmly.ui.home.TrendingResults
import com.example.filmly.ui.search.Movie
import com.example.filmly.ui.search.MovieResults
import com.example.filmly.ui.search.PersonResults
import com.example.filmly.ui.search.TvResults
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("trending/{type}/{time}")
    suspend fun getTrending(
        @Path("type") type: String,
        @Path("time") time: String,
        @Query("apikey") apikey: String
    ): TrendingResults

    @GET("search/movie?language=pt-BR")
    suspend fun getSearchMovie(
        @Query("apikey") apikey: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): MovieResults

    @GET("search/tv?language=pt-BR")
    suspend fun getSearchTv(
        @Query("apikey") apikey: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): TvResults

    @GET("search/person?language=pt-BR")
    suspend fun getSearchPerson(
        @Query("apikey") apikey: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): PersonResults



}

object TmdbApiteste{
    val retrofitService: TmdbApi by lazy {
        retrofit.create(TmdbApi::class.java)
    }
}







val contentType = "application/json".toMediaType()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(Json.asConverterFactory(contentType))
    .build()

