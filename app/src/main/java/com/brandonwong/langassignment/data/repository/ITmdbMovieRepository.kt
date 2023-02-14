package com.brandonwong.langassignment.data.repository

import com.brandonwong.langassignment.data.`object`.*

interface ITmdbMovieRepository {
    suspend fun getPopularMovies(page: Int): List<MovieInfo>
    suspend fun syncFavoriteMovie(movieId: Int, isFavorite: Boolean): SyncResultInfo
}