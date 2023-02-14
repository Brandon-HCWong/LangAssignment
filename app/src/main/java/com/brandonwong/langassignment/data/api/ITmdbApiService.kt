package com.brandonwong.langassignment.data.api

import com.brandonwong.langassignment.BuildConfig
import com.brandonwong.langassignment.data.api.entity.FavoriteMoviePostBody
import com.brandonwong.langassignment.data.api.entity.FavoriteMovieSyncResultEntity
import com.brandonwong.langassignment.data.api.entity.MoviePageEntity
import retrofit2.http.*

interface ITmdbApiService {
    @GET("movie/popular")
    suspend fun requestPopularMovies(
        @Query("page") page: Int,
    ): MoviePageEntity

    @GET("account/${BuildConfig.TMBD_ACCOUNT_ID}/favorite/movies")
    suspend fun requestFavoriteMovies(
        @Query("page") page: Int,
    ): MoviePageEntity

    @POST("account/${BuildConfig.TMBD_ACCOUNT_ID}/favorite")
    suspend fun syncFavoriteMovie(
        @Body favoriteMoviePostBody: FavoriteMoviePostBody,
    ): FavoriteMovieSyncResultEntity
}