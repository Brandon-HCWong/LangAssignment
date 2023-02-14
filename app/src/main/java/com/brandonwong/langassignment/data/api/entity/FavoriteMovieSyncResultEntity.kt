package com.brandonwong.langassignment.data.api.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoriteMovieSyncResultEntity(
    @Json(name = "success")
    val isSuccess: Boolean,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)