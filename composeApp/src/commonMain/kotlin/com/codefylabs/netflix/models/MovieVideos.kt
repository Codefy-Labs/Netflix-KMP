package com.codefylabs.netflix.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesVideos(
    val results: List<MovieVideoItem>
){
    val trailer : String? = results.find { it.type == "Trailer" }?.url
    val clip : String? = results.find { it.type == "Clip" }?.url
    val other : String? = results.find { it.type != "Clip" && it.type != "Trailer"}?.url
}

@Serializable
data class MovieVideoItem(
    val key: String,
    val name: String,
    val size: Int,
    val type: String
){

    val url = "https://www.youtube.com/watch?v=$key"

}