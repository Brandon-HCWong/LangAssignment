package com.brandonwong.langassignment.data.repository

import com.brandonwong.langassignment.data.api.ITmdbApiService
import com.brandonwong.langassignment.data.api.entity.FavoriteMovieSyncResultEntity
import com.brandonwong.langassignment.data.api.entity.MovieEntity
import com.brandonwong.langassignment.data.api.entity.MoviePageEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTestRule

@OptIn(ExperimentalCoroutinesApi::class)
class TmdbMovieRepositoryTest {
    private lateinit var tmdbMovieRepository: TmdbMovieRepository
    private val tmdbApiService: ITmdbApiService = mockk(relaxed = true)
    private val module = module {
        single { tmdbApiService }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        modules(module)
    }

    @Before
    fun setUp() {
        tmdbMovieRepository = TmdbMovieRepository()
    }

    @Test
    fun testGetPopularMovies_emptyFavoriteMovieId() = runTest {
        // Arrange
        val dummyMoviePageEntity = MoviePageEntity(
            page = 1,
            movies = arrayListOf(
                MovieEntity(505642, "Black Panther: Wakanda Forever", "Just a movie.", "/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg")
            ),
            totalPages = 1
        )
        val dummyFavoriteMoviePageEntity = MoviePageEntity(
            page = 1,
            movies = arrayListOf(),
            totalPages = 1
        )
        coEvery { tmdbApiService.requestPopularMovies(any()) } returns dummyMoviePageEntity
        coEvery { tmdbApiService.requestFavoriteMovies(any()) } returns dummyFavoriteMoviePageEntity

        // Act
        val movieInfoList = tmdbMovieRepository.getPopularMovies(1)

        // Assert
        Assert.assertEquals(1, movieInfoList.size)
        Assert.assertEquals(false, movieInfoList[0].isFavorite)
    }

    @Test
    fun testGetPopularMovies_nonemptyFavoriteMovieId() = runTest {
        // Arrange
        val dummyMoviePageEntity = MoviePageEntity(
            page = 1,
            movies = arrayListOf(
                MovieEntity(505642, "Black Panther: Wakanda Forever", "Just a movie.", "/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg"),
                MovieEntity(315162, "Puss in Boots: The Last Wish", "Just a movie 2.", "/tGwO4xcBjhXC0p5qlkw37TrH6S6.jpg"),
            ),
            totalPages = 1
        )
        val dummyFavoriteMoviePageEntity = MoviePageEntity(
            page = 1,
            movies = arrayListOf(
                MovieEntity(315162, "Puss in Boots: The Last Wish", "Just a movie 2.", "/tGwO4xcBjhXC0p5qlkw37TrH6S6.jpg")
            ),
            totalPages = 1
        )
        coEvery { tmdbApiService.requestPopularMovies(any()) } returns dummyMoviePageEntity
        coEvery { tmdbApiService.requestFavoriteMovies(any()) } returns dummyFavoriteMoviePageEntity

        // Act
        val movieInfoList = tmdbMovieRepository.getPopularMovies(1)

        // Assert
        Assert.assertEquals(2, movieInfoList.size)
        Assert.assertEquals(true, movieInfoList[1].isFavorite)
    }

    @Test
    fun testSyncFavoriteMovie_addSuccess() = runTest {
        // Arrange
        val syncResultEntity = FavoriteMovieSyncResultEntity(true, 1, "Success.")
        coEvery { tmdbApiService.syncFavoriteMovie(any()) } returns syncResultEntity

        // Act
        val syncResultInfo = tmdbMovieRepository.syncFavoriteMovie(315162, true)
        val favoriteMovieIdSet = tmdbMovieRepository.getFavoriteMovieIdSet()

        // Assert
        Assert.assertEquals(true, syncResultInfo.isSuccess)
        Assert.assertEquals(true, favoriteMovieIdSet.contains(315162))
    }

    @Test
    fun testSyncFavoriteMovie_removeSuccess() = runTest {
        // Arrange
        val addSyncResultEntity = FavoriteMovieSyncResultEntity(true, 1, "Success.")
        val removeSyncResultEntity = FavoriteMovieSyncResultEntity(true, 13, "The item/record was deleted successfully..")
        coEvery { tmdbApiService.syncFavoriteMovie(any()) } returns addSyncResultEntity
        tmdbMovieRepository.syncFavoriteMovie(315162, true)
        coEvery { tmdbApiService.syncFavoriteMovie(any()) } returns removeSyncResultEntity

        // Act
        val syncResultInfo = tmdbMovieRepository.syncFavoriteMovie(315162, false)
        val favoriteMovieIdSet = tmdbMovieRepository.getFavoriteMovieIdSet()

        // Assert
        Assert.assertEquals(true, syncResultInfo.isSuccess)
        Assert.assertEquals(false, favoriteMovieIdSet.contains(315162))
    }
}