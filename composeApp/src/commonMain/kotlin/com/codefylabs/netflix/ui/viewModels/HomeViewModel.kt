package com.codefylabs.netflix.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codefylabs.netflix.models.Movie
import com.codefylabs.netflix.network.MoviesApi
import com.codefylabs.netflix.network.NetworkRepository
import com.codefylabs.netflix.network.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
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
        Napier.e("FetchedMovies")
        viewModelScope.launch {
            _uiState.value = HomeViewState.Loading

            val topRatedMoviesDeferred =
                async { repository.getTopRatedMovies(MoviesApi.LANG_ENG, 1) }
            val nowPlayingMoviesDeferred =
                async { repository.getNowPlayingMovies(MoviesApi.LANG_ENG, 1) }
            val popularMoviesDeferred = async { repository.getPopularMovies(MoviesApi.LANG_ENG, 1) }

            val topRatedMoviesResult = topRatedMoviesDeferred.await()
            val nowPlayingMoviesResult = nowPlayingMoviesDeferred.await()
            val popularMoviesResult = popularMoviesDeferred.await()

            val topRatedMovies = when (topRatedMoviesResult) {
                is Result.Failure -> {
                    Napier.e(topRatedMoviesResult.exception.message.toString())
                    topRatedMoviesResult.exception.printStackTrace()
                    emptyList()
                }

                is Result.Success -> topRatedMoviesResult.data.results
            }

            val nowPlayingMovies = when (nowPlayingMoviesResult) {
                is Result.Failure -> {
                    Napier.e(nowPlayingMoviesResult.exception.message.toString())
                    nowPlayingMoviesResult.exception.printStackTrace()
                    emptyList()
                }

                is Result.Success -> nowPlayingMoviesResult.data.results
            }

            val popularMovies = when (popularMoviesResult) {
                is Result.Failure -> {
                    Napier.e(popularMoviesResult.exception.message.toString())
                    popularMoviesResult.exception.printStackTrace()
                    emptyList()
                }

                is Result.Success -> popularMoviesResult.data.results
            }



            Napier.i("TopRatedMovies -> $topRatedMovies")
            Napier.i("\n\n")
            Napier.i("NowPlayingMovies -> $nowPlayingMovies")
            Napier.i("\n\n")
            Napier.i("PopularMovies -> $popularMovies")

            _uiState.value = HomeViewState.Dashboard(
                topRatedMovies = topRatedMovies,
                trendingMovies = nowPlayingMovies,
                popularMovies = popularMovies
            )
        }
    }
}


sealed interface HomeViewState {
    data class Dashboard(
        val topRatedMovies: List<Movie> = emptyList(),
        val popularMovies: List<Movie> = emptyList(),
        val trendingMovies: List<Movie> = emptyList(),
    ) : HomeViewState

    data object Loading : HomeViewState
}