package com.codefylabs.netflix.models

import kotlinx.serialization.SerialName


data class Movies(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

data class MovieDto(
    val id: Long,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backDropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
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
)

data class SimilarMoviesDto(
    val results: List<SimilarMovieDto>
)

data class SimilarMovieDto(
    val id: Int,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backDropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("original_language")
    val originalLanguage: String,
    val title: String,
    val overview: String,
    val video: Boolean,
)