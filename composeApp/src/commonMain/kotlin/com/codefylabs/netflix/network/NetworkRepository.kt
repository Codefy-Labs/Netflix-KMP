package com.codefylabs.netflix.network

import com.codefylabs.netflix.models.MovieDetails
import com.codefylabs.netflix.models.Movies
import com.codefylabs.netflix.models.MoviesVideos
import com.codefylabs.netflix.models.SimilarMovies
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class NetworkRepository(private val client: HttpClient) {

    suspend fun getTopRatedMovies(language: String, page: Int): Result<Movies> =
        makeRequest {
            client.get(MoviesApi.BASE_URL.plus(MoviesApi.ENDPOINT_TOP_RATED)) {
                url {
                    parameters.append("language", language)
                    parameters.append("page", page.toString())
                    parameters.append("api_key", MoviesApi.API_KEY)
                }
            }.body()
        }

    suspend fun getNowPlayingMovies(language: String, page: Int): Result<Movies> =
        makeRequest {
            client.get(MoviesApi.BASE_URL.plus(MoviesApi.ENDPOINT_NOW_PLAYING)) {
                url {
                    parameters.append("language", language)
                    parameters.append("page", page.toString())
                    parameters.append("api_key", MoviesApi.API_KEY)
                }
            }.body()
        }

    suspend fun getPopularMovies(language: String, page: Int): Result<Movies> =
        makeRequest {
            client.get(MoviesApi.BASE_URL.plus(MoviesApi.ENDPOINT_POPULAR)) {
                url {
                    parameters.append("language", language)
                    parameters.append("page", page.toString())
                    parameters.append("api_key", MoviesApi.API_KEY)
                }
            }.body()
        }

    suspend fun getMovieById(movieId: Long): Result<MovieDetails> =
        makeRequest {
            client.get(
                MoviesApi.BASE_URL.plus(
                    MoviesApi.ENDPOINT_MOVIE.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            ) {
                url {
                    parameters.append("api_key", MoviesApi.API_KEY)
                }
            }.body()
        }

    suspend fun getMovieVideosById(movieId: Long): Result<MoviesVideos> =
        makeRequest {
            client.get(
                MoviesApi.BASE_URL.plus(
                    MoviesApi.ENDPOINT_MOVIE_VIDEO.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            ) {
                url {
                    parameters.append("api_key", MoviesApi.API_KEY)
                }
            }.body()
        }

    suspend fun getSimilarMovies(movieId: Long): Result<SimilarMovies> =
        makeRequest {
            client.get(
                MoviesApi.BASE_URL.plus(
                    MoviesApi.ENDPOINT_SIMILAR_MOVIES.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            ) {
                url {
                    parameters.append("api_key", MoviesApi.API_KEY)
                }
            }.body()
        }

    private suspend inline fun <reified T> makeRequest(crossinline request: suspend () -> HttpResponse): Result<T> {
        return try {
            val response: HttpResponse = request()
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body())
            } else {
                Napier.e(response.bodyAsText())
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}


