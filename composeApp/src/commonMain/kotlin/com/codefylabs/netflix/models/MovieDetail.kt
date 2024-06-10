package com.codefylabs.netflix.models

import com.codefylabs.netflix.network.MoviesApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val id: Long,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backDropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("genres")
    val genres: List<Genres>,
    @SerialName("original_language")
    val originalLanguage: String,
    val title: String,
    val overview: String,
    val popularity: Double,
    @SerialName("release_date")
    val releaseDate: String,
    val video: Boolean,
    @SerialName("vote_average")
    val avgVote: Double,
    @SerialName("vote_count")
    val voteCount: Int
){

    val backDropUrl = MoviesApi.IMAGE_BASE_URL_W500 + backDropPath
    val posterUrl = MoviesApi.IMAGE_BASE_URL_W500 + posterPath

}

@Serializable
data class Genres(
    val id: Int,
    val name: String
)