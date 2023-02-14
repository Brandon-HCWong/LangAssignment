package com.brandonwong.langassignment.data.api.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoriteMoviePostBody(
    @Json(name = "media_type")
    val mediaType: String = "movie",
    @Json(name = "media_id")
    val mediaId: Int,
    @Json(name = "favorite")
    val isFavorite: Boolean
)