package com.codefylabs.netflix.models

data class MoviesVideosDto(
    val results: List<MovieVideoItemDto>
)

data class MovieVideoItemDto(
    val key: String,
    val name: String,
    val size: Int,
    val type: String
)