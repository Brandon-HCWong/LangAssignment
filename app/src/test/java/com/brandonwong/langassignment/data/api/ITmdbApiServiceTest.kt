package com.brandonwong.langassignment.data.api

import com.brandonwong.langassignment.BuildConfig
import com.brandonwong.langassignment.data.api.entity.FavoriteMoviePostBody
import com.brandonwong.langassignment.data.provider.ApiInfoProvider
import com.brandonwong.langassignment.data.provider.MoshiProvider
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ITmdbApiServiceTest {
    private lateinit var tmdbApiService: ITmdbApiService

    @Before
    fun setUp() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.TMBD_ACCESS_KEY}")
                    .build()
                chain.proceed(newRequest)
            }.also {
                it.addInterceptor(HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG) {
                        true -> HttpLoggingInterceptor.Level.BODY
                        else -> HttpLoggingInterceptor.Level.NONE
                    }
                })
            }.build()
        tmdbApiService = Retrofit.Builder()
            .baseUrl(ApiInfoProvider.geTmdbApiBaseUrl())
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(MoshiProvider.moshiDefaultIfNull))
            .build()
            .create(ITmdbApiService::class.java)
    }

    @Test
    fun testRequestPopularMovies() {
        runBlocking {
            val result = tmdbApiService.requestPopularMovies(1)
            if (result.page != 1) {
                Assert.fail("[Failure]")
            }
            println("[Success] result: ${result.page}")
        }
    }

    @Test
    fun testRequestFavoriteMovies() {
        runBlocking {
            val result = tmdbApiService.requestFavoriteMovies(1)
            if (result.page != 1) {
                Assert.fail("[Failure]")
            }
            println("[Success] result: ${result.page}")
        }
    }

    @Test
    fun testAddFavoriteMovie() {
        runBlocking {
            val result = tmdbApiService.syncFavoriteMovie(FavoriteMoviePostBody(
                mediaId = 315162,
                isFavorite = true
            ))
            if (result.isSuccess && (result.statusCode == 1 || result.statusCode == 12)) {
                println("[Success] result: $result")
            } else {
                Assert.fail("[Failure]")
            }
        }
    }

    @Test
    fun testRemoveFavoriteMovie() {
        runBlocking {
            val result = tmdbApiService.syncFavoriteMovie(FavoriteMoviePostBody(
                mediaId = 315162,
                isFavorite = false
            ))
            if (result.isSuccess && (result.statusCode == 1 || result.statusCode == 13)) {
                println("[Success] result: $result")
            } else {
                Assert.fail("[Failure]")
            }
        }
    }
}