package com.codefylabs.netflix.ui.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codefylabs.netflix.models.Movie
import com.codefylabs.netflix.ui.common.SmallMovieItem
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowRight


@Composable
fun PopularOnNetflixSection(
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    movies: List<Movie>,
) {
    Column(modifier = modifier) {
        SectionLabel("Popular", viewAll = {})
        Spacer(modifier = Modifier.height(6.dp))
        TrendingNowMoviesCarousel(movies = movies, onMovieSelected = onMovieClick)
    }
}

@Composable
private fun TrendingNowMoviesCarousel(
    movies: List<Movie>,
    onMovieSelected: (Long) -> Unit,
) {
    LazyRow(modifier = Modifier.padding(start = 8.dp)) {
        items(movies) { movie ->
            SmallMovieItem(movie, onMovieSelected = onMovieSelected)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}