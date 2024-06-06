package com.codefylabs.netflix.ui.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codefylabs.netflix.models.Movie
import com.codefylabs.netflix.ui.common.HighlightMovieItem
import com.codefylabs.netflix.ui.common.NetflixSurface
import com.codefylabs.netflix.ui.common.ShimmerEffect
import com.codefylabs.netflix.ui.features.home.components.InfoButton
import com.codefylabs.netflix.ui.features.home.components.MyListButton
import com.codefylabs.netflix.ui.features.home.components.PlayButton
import com.codefylabs.netflix.ui.features.home.components.PopularOnNetflixSection
import com.codefylabs.netflix.ui.features.home.components.TopRatedMoviesSection
import com.codefylabs.netflix.ui.features.home.components.TrendingNowSection
import com.codefylabs.netflix.ui.viewModels.HomeViewModel
import com.codefylabs.netflix.ui.viewModels.HomeViewState
import io.github.aakira.napier.Napier
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    scrollState: LazyListState = rememberLazyListState(),
    viewModel: HomeViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState(HomeViewState.Loading)

    NetflixSurface(
        color = MaterialTheme.colorScheme.background,
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 140.dp)
        ) {

            when (val state = uiState) {
                is HomeViewState.Dashboard -> {
                    item {
                        state.trendingMovies.firstOrNull()?.let { movie ->
                            TopHighlightedMovie(
                                onMovieClick = {

                                },
                                topMovie = movie
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TopRatedMoviesSection(
                            onMovieClick = { movieId ->

                            },
                            movies = state.topRatedMovies
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TrendingNowSection(
                            onMovieClick = { movieId ->

                            },
                            movies = state.trendingMovies
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        PopularOnNetflixSection(
                            onMovieClick = { movieId ->

                            },
                            movies = state.popularMovies
                        )


                    }
                }

                HomeViewState.Loading -> {

                }
            }


        }
    }
}


@Composable
private fun TopHighlightedMovie(
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    topMovie: Movie,
) {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (movieImage, buttonPanel, topTrendingBanner) = createRefs()
        HighlightMovieItem(topMovie, onMovieClick,
            modifier = modifier.constrainAs(movieImage) {
                top.linkTo(parent.top)
            }
        )
        TopTrendingBanner(
            modifier = modifier.constrainAs(topTrendingBanner) {
                bottom.linkTo(buttonPanel.top, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Row(
            modifier = modifier
                .constrainAs(buttonPanel) {
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                }
        ) {
            MyListButton(modifier = modifier.weight(1f))
            PlayButton(isPressed = mutableStateOf(true), modifier = modifier.weight(1f))
            InfoButton(modifier = modifier.weight(1f))
        }
    }
}

@Composable
private fun TopTrendingBanner(
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(10),
            modifier = modifier
                .size(
                    width = 28.dp,
                    height = 28.dp
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TOP",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 0.sp,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    maxLines = 1
                )
                Text(
                    text = "10",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 0.sp,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "#2 in Canada Today",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                letterSpacing = (-0.10).sp,
                fontWeight = FontWeight.Bold,
            ),
            maxLines = 1
        )
    }
}
