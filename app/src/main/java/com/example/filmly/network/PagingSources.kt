package com.example.filmly.network

import androidx.paging.PagingSource
import com.example.filmly.ui.home.PopularActor
import com.example.filmly.ui.home.PopularMovie
import com.example.filmly.ui.home.PopularTV
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val service: TmdbApi
) : PagingSource<Int, PopularMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getAllPopularMovies(position)
            val movies = response.results
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}

class TVPagingSource(
    private val service: TmdbApi
) : PagingSource<Int, PopularTV>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularTV> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getAllPopularTV(position)
            val series = response.results
            val nextKey = if (series.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = series,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}

class ActorsPagingSource(
    private val service: TmdbApi
) : PagingSource<Int, PopularActor>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularActor> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getAllPopularActors(position)
            val actors = response.results
            val nextKey = if (actors.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = actors,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}