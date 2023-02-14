package com.brandonwong.langassignment.data.repository

import com.brandonwong.langassignment.data.api.ITmdbApiService
import com.brandonwong.langassignment.data.api.entity.*
import com.brandonwong.langassignment.data.`object`.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TmdbMovieRepository : ITmdbMovieRepository, KoinComponent {
    private val tmdbApiService: ITmdbApiService by inject()
    private val favoriteMovieIdSet = mutableSetOf<Int>()

    fun getFavoriteMovieIdSet() = favoriteMovieIdSet.toMutableSet()

    override suspend fun getPopularMovies(page: Int): List<MovieInfo> = withContext(Dispatchers.IO) {
        if (favoriteMovieIdSet.isEmpty()) {
            syncFavoriteMovieIds()
        }
        val entity = tmdbApiService.requestPopularMovies(page)
        return@withContext entity.movies.map {
            MovieInfo.fromMovieEntity(it, favoriteMovieIdSet.contains(it.id))
        }
    }

    override suspend fun syncFavoriteMovie(movieId: Int, isFavorite: Boolean): SyncResultInfo = withContext(Dispatchers.IO) {
        return@withContext SyncResultInfo.fromFavoriteMovieSyncResultEntity(
            tmdbApiService.syncFavoriteMovie(
                FavoriteMoviePostBody(
                    mediaId = movieId,
                    isFavorite = isFavorite
                )
            ).also {
                if (it.isSuccess) {
                    if (isFavorite) {
                        favoriteMovieIdSet.add(movieId)
                    } else {
                        favoriteMovieIdSet.remove(movieId)
                    }
                }
            }
        )
    }

    private suspend fun syncFavoriteMovieIds() {
        if (favoriteMovieIdSet.isNotEmpty()) {
            return
        }
        var page = 1
        var totalPages = 1
        val movieEntityList = arrayListOf<MovieEntity>()
        tmdbApiService.requestFavoriteMovies(page).let {
            movieEntityList.addAll(it.movies)
            totalPages = it.totalPages
        }
        while (page <= totalPages) {
            movieEntityList.addAll(tmdbApiService.requestFavoriteMovies(++page).movies)
        }
        favoriteMovieIdSet.clear()
        movieEntityList.forEach {
            favoriteMovieIdSet.add(it.id)
        }
    }
}