package com.brandonwong.langassignment.data.`object`

import com.brandonwong.langassignment.data.api.entity.FavoriteMovieSyncResultEntity
import com.brandonwong.langassignment.data.api.entity.MovieEntity
import com.squareup.moshi.Json

data class SyncResultInfo(
    val isSuccess: Boolean,
    val statusCode: Int,
    val statusMessage: String
) {
    companion object {
        fun fromFavoriteMovieSyncResultEntity(entity: FavoriteMovieSyncResultEntity): SyncResultInfo {
            return SyncResultInfo(
                entity.isSuccess,
                entity.statusCode,
                entity.statusMessage
            )
        }
    }
}
