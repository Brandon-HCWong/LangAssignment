package com.brandonwong.langassignment.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brandonwong.langassignment.data.`object`.MovieInfo
import com.brandonwong.langassignment.data.repository.ITmdbMovieRepository

class MoviePagingSource(
    private val tmdbMovieRepository: ITmdbMovieRepository
) : PagingSource<Int, MovieInfo>() {
    private val MAX_PAGE_SIZE = 3

    init {
        Log.d("BrandonDebug", "[MoviePagingSource - init]")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieInfo> {
        Log.d("BrandonDebug", "[MoviePagingSource - load] : params.key = ${params.key}")
        try {
            val currentIndex = params.key ?: 1
            val previousKey = if (currentIndex > 1) {
                currentIndex - 1
            } else {
                null
            }
            val nextKey = if (currentIndex != MAX_PAGE_SIZE) {
                currentIndex + 1
            } else {
                null
            }
            Log.d("BrandonDebug", "[MoviePagingSource - load] : previous current next  = ($previousKey, $currentIndex, $nextKey)")
            val movieInfoList = tmdbMovieRepository.getPopularMovies(currentIndex)
            if (currentIndex == 1 && movieInfoList.isEmpty()) {
                return LoadResult.Error(MoviesEmptyException())
            }
            return LoadResult.Page(
                movieInfoList,
                previousKey,
                nextKey
            )
        } catch (e: Exception) {
            Log.d("BrandonDebug", "[MoviePagingSource - load] : Exception  = $e")
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieInfo>): Int? {
        Log.d("BrandonDebug", "[MoviePagingSource - getRefreshKey] : state.anchorPosition  = ${state.anchorPosition}")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    class MoviesEmptyException: Throwable()
}