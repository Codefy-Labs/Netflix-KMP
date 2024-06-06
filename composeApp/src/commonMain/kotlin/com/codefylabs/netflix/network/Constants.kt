package com.codefylabs.netflix.network

object MoviesApi {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val ENDPOINT_POPULAR = "movie/popular"
    const val ENDPOINT_TOP_RATED = "movie/top_rated"
    const val ENDPOINT_NOW_PLAYING = "movie/now_playing"
    const val ENDPOINT_MOVIE = "movie/{movie_id}"
    const val ENDPOINT_MOVIE_VIDEO = "movie/{movie_id}/videos"
    const val ENDPOINT_SIMILAR_MOVIES = "movie/{movie_id}/similar"

    const val LANG_ENG = "en-US"
    const val IMAGE_BASE_URL_W500 = "https://image.tmdb.org/t/p/w500"

    const val SAMPLE_VIDEO_URL =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    const val API_KEY = "4ba64cef02ede71bfe29f670f6e005e0"
}