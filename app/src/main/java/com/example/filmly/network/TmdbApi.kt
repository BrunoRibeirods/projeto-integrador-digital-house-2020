package com.example.filmly.network

import com.example.filmly.ui.home.TrendingResults
import com.example.filmly.ui.search.MovieResults
import com.example.filmly.ui.search.PersonResults
import com.example.filmly.ui.search.TvResults
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient

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




}



object TmdbApiteste{
    val retrofitService: TmdbApi by lazy {
        retrofitCreate().create(TmdbApi::class.java)
    }

    val httpClient = OkHttpClient.Builder()

//    fun getHttp() {
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        httpClient.addInterceptor(logging)
//    }



    fun retrofitCreate(): Retrofit {
        val contentType = "application/json".toMediaType()
//        getHttp()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory(contentType))
            .client(httpClient.build())
            .build()
    }




}


