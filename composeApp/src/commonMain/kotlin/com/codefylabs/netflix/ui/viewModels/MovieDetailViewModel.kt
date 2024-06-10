package com.codefylabs.netflix.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codefylabs.netflix.models.Movie
import com.codefylabs.netflix.models.MovieDetails
import com.codefylabs.netflix.models.Movies
import com.codefylabs.netflix.models.MoviesVideos
import com.codefylabs.netflix.network.NetworkRepository
import com.codefylabs.netflix.network.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import netflix_kmp.composeapp.generated.resources.Res

class MovieDetailViewModel(
    private val movieId: Long,
    private val repository: NetworkRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val uiState: StateFlow<MovieDetailState> = _uiState.asStateFlow()

    init {
        fetchMovieDetail()
    }

    fun fetchMovieDetail() {
        viewModelScope.launch {
            _uiState.value = MovieDetailState.Loading

            val movieResult = repository.getMovieById(movieId = movieId)
            val similarMoviesResult = repository.getSimilarMovies(movieId = movieId)

            if (movieResult is Result.Success   && similarMoviesResult is Result.Success) {
                _uiState.value = MovieDetailState.Details(
                    movie = movieResult.data,
                    similarMovies = similarMoviesResult.data.results
                )
            } else {
                val errorMessage = (movieResult as? Result.Failure)?.exception?.message
                    ?: "Something went wrong! Please refresh.."
                _uiState.value = MovieDetailState.Error(message = errorMessage)
            }
        }
    }

}

sealed interface MovieDetailState {
    data class Details(
        val movie: MovieDetails,
        val similarMovies: List<Movie>,
    ) : MovieDetailState

    data class Error(val message: String) : MovieDetailState

    data object Loading : MovieDetailState
}