package com.example.filmly.network

import androidx.paging.PagingSource
import com.example.filmly.ui.home.PopularMovie
import retrofit2.HttpException
import java.io.IOException

private const val MOVIES_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val service: TmdbApi
) : PagingSource<Int, PopularMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovie> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
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
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}