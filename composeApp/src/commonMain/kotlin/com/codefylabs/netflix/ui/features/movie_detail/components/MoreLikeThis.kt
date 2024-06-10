package com.codefylabs.netflix.ui.features.movie_detail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codefylabs.netflix.models.Movie
import com.codefylabs.netflix.ui.common.SmallMovieItem
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import netflix_kmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun MoreLikeThis(modifier: Modifier = Modifier, similarMovies: List<Movie>) {
    PhotoGrid(movies = similarMovies, modifier = modifier)
}

@Composable
fun PhotoGrid(modifier: Modifier = Modifier, movies: List<Movie?>) {
    Column(modifier = modifier) {
        movies.chunked(4).forEach { movieTriplet ->
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                movieTriplet[0]?.let {
                    SmallMovieItem(
                        it,
                        onMovieSelected = {},
                        modifier = Modifier.padding(6.dp)
                    )
                }
                movieTriplet[1]?.let {
                    SmallMovieItem(
                        it,
                        onMovieSelected = {},
                        modifier = Modifier.padding(6.dp)
                    )
                }
                movieTriplet[2]?.let {
                    SmallMovieItem(
                        it,
                        onMovieSelected = {},
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TrailersAndMore(modifier: Modifier = Modifier) {
    var bytes by remember {
        mutableStateOf(ByteArray(0))
    }
    LaunchedEffect(Unit) {
        bytes = Res.readBytes("files/loading.json")
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.JsonString(
            bytes.decodeToString()
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LottieAnimation(
            composition,
            modifier = Modifier
                .size(250.dp)
                .padding(10.dp)
                .align(Alignment.Center)
                .then(modifier),
            restartOnPlay = true,
            reverseOnRepeat = true,
            iterations = Int.MAX_VALUE
        )
    }
}