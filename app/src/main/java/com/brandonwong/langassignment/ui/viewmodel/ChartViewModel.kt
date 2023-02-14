package com.brandonwong.langassignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.brandonwong.langassignment.data.api.ITmdbApiService
import com.brandonwong.langassignment.data.repository.IPagingSourceRepository
import com.brandonwong.langassignment.data.repository.ITmdbMovieRepository

class ChartViewModel(
    private val pagingSourceRepository: IPagingSourceRepository,
    private val tmdbMovieRepository: ITmdbMovieRepository
) : ViewModel() {
    private val PAGE_SIZE = 1
    private val pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            pagingSourceRepository.getMoviePagingSource()
        }
    )
    val rowPagingDataFlow = pager.flow.cachedIn(viewModelScope)

    suspend fun syncFavoriteMovie(movieId: Int, isFavorite: Boolean) = tmdbMovieRepository.syncFavoriteMovie(movieId, isFavorite)
}