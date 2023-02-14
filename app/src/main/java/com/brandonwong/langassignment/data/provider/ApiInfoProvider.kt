package com.brandonwong.langassignment.data.provider

object ApiInfoProvider {
    fun getUnsplashApiBaseUrl(): String {
        return "https://api.unsplash.com/"
    }
    fun geTmdbApiBaseUrl(): String {
        return "https://api.themoviedb.org/3/"
    }
    fun geTmdbImageBaseUrl(): String {
        return "https://image.tmdb.org/t/p/original"
    }
}