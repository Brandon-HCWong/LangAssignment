package com.brandonwong.langassignment.data.api.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviePageEntity(
    val page: Int,
    @Json(name = "results")
    val movies: List<MovieEntity>,
    @Json(name = "total_pages")
    val totalPages: Int,
)

@JsonClass(generateAdapter = true)
data class MovieEntity(
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "overview")
    val description: String,
    @Json(name = "poster_path")
    val posterPath: String,
)
