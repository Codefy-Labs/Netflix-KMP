package com.codefylabs.netflix.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesVideos(
    val results: List<MovieVideoItem>
)

@Serializable
data class MovieVideoItem(
    val key: String,
    val name: String,
    val size: Int,
    val type: String
)