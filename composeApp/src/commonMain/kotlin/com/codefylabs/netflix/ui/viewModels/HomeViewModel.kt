package com.codefylabs.netflix.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codefylabs.netflix.models.Movie
import com.codefylabs.netflix.models.Movies
import com.codefylabs.netflix.network.MoviesApi
import com.codefylabs.netflix.network.NetworkRepository
import com.codefylabs.netflix.network.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NetworkRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val uiState: StateFlow<HomeViewState> = _uiState.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _uiState.value = HomeViewState.Loading

            try {
                val (topRatedMoviesResult, nowPlayingMoviesResult, popularMoviesResult) = awaitAll(
                    async { repository.getTopRatedMovies(MoviesApi.LANG_ENG, 1) },
                    async { repository.getNowPlayingMovies(MoviesApi.LANG_ENG, 1) },
                    async { repository.getPopularMovies(MoviesApi.LANG_ENG, 1) }
                )

                val topRatedMovies = handleResult(topRatedMoviesResult)
                val nowPlayingMovies = handleResult(nowPlayingMoviesResult).reversed()
                val popularMovies = handleResult(popularMoviesResult)

                _uiState.value = HomeViewState.Dashboard(
                    topRatedMovies = topRatedMovies,
                    trendingMovies = nowPlayingMovies,
                    popularMovies = popularMovies
                )
            } catch (e: Exception) {
                Napier.e("Error fetching movies: ${e.message}")
                e.printStackTrace()
                _uiState.value = HomeViewState.Error("Failed to fetch movies.")
            }
        }
    }

    private fun handleResult(result: Result<Movies>): List<Movie> {
        return when (result) {
            is Result.Failure -> throw result.exception
            is Result.Success -> result.data.results
        }
    }
}


sealed interface HomeViewState {
    data class Dashboard(
        val topRatedMovies: List<Movie> = emptyList(),
        val popularMovies: List<Movie> = emptyList(),
        val trendingMovies: List<Movie> = emptyList(),
    ) : HomeViewState

    data class Error(val message: String) : HomeViewState

    data object Loading : HomeViewState
}