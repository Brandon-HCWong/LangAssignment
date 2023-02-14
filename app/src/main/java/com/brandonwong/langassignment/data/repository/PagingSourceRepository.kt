package com.brandonwong.langassignment.data.repository

import com.brandonwong.langassignment.data.paging.MoviePagingSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PagingSourceRepository : IPagingSourceRepository, KoinComponent {
    private val tmdbMovieRepository: ITmdbMovieRepository by inject()

    override fun getMoviePagingSource(): MoviePagingSource = MoviePagingSource(tmdbMovieRepository)
}