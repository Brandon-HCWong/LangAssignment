package com.brandonwong.langassignment.data.`object`

import com.brandonwong.langassignment.data.api.entity.MovieEntity
import java.io.Serializable

data class MovieInfo(
    val id: Int,
    val originalTitle: String,
    val description: String,
    val posterPath: String,
    var isFavorite: Boolean = false
): Serializable {
    companion object {
        fun fromMovieEntity(movieEntity: MovieEntity, isFavorite: Boolean = false): MovieInfo {
            return MovieInfo(
                movieEntity.id,
                movieEntity.originalTitle,
                movieEntity.description,
                movieEntity.posterPath,
                isFavorite
            )
        }
    }
}
