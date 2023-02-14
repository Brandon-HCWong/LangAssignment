package com.brandonwong.langassignment.data.repository

import com.brandonwong.langassignment.data.paging.MoviePagingSource

interface IPagingSourceRepository {
    fun getMoviePagingSource(): MoviePagingSource
}