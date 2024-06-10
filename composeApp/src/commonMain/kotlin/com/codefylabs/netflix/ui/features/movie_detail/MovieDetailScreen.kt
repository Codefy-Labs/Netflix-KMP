package com.codefylabs.netflix.ui.features.movie_detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.codefylabs.netflix.models.MovieDetails
import com.codefylabs.netflix.models.MoviesVideos
import com.codefylabs.netflix.ui.common.ErrorBox
import com.codefylabs.netflix.ui.common.MovieDetailAppBar
import com.codefylabs.netflix.ui.common.RoundedCornerRemoteImage
import com.codefylabs.netflix.ui.features.home.components.DownloadButton
import com.codefylabs.netflix.ui.features.home.components.PlayButton
import com.codefylabs.netflix.ui.features.movie_detail.components.MoreMovies
import com.codefylabs.netflix.ui.viewModels.MovieDetailState
import com.codefylabs.netflix.ui.viewModels.MovieDetailViewModel
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import netflix_kmp.composeapp.generated.resources.Res
import netflix_kmp.composeapp.generated.resources.ic_netflix_symbol
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieDetailScreen(upPress: () -> Unit, viewModel: MovieDetailViewModel) {

    val uiState by viewModel.uiState.collectAsState(MovieDetailState.Loading)

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        when (val state = uiState) {
            is MovieDetailState.Details -> {
                Box {
                    MovieBanner(state.movie.backDropUrl)
                    MovieDetailAppBar(upPress = upPress)
                }

                MovieDetailLayout(
                    modifier = Modifier.padding(16.dp),
                    movie = state.movie
                )
                Spacer(modifier = Modifier.height(20.dp))
                MoreMovies(similarMovies = state.similarMovies)
            }

            is MovieDetailState.Error -> {
                ErrorBox(errorMessage = state.message, onRetry = viewModel::fetchMovieDetail)
            }

            MovieDetailState.Loading -> {
                LoadingBox()
            }
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoadingBox(modifier: Modifier = Modifier) {
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .then(modifier),
            restartOnPlay = true,
            iterations = Int.MAX_VALUE
        )
    }
}

@Composable
private fun MovieDetailLayout(modifier: Modifier, movie: MovieDetails) {
    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(Res.drawable.ic_netflix_symbol),
                contentDescription = "Netflix logo",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { }
            )
            Text(
                text = "FILM",
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically),
                letterSpacing = 2.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = movie.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "98% Match",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff65b562)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = movie.releaseDate.substring(0, 4),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8)
                    )
                    .padding(start = 4.dp, top = 1.dp, end = 4.dp, bottom = 1.dp)
            ) {
                Text(
                    text = "7+",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "1h 25m",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8)
                    )
                    .padding(start = 3.dp, top = 0.dp, end = 3.dp, bottom = 0.dp)
            ) {
                Text(
                    text = "HD",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    letterSpacing = 2.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        PlayButton(
            isPressed = remember { mutableStateOf(true) },
            modifier = Modifier.fillMaxWidth(),
            cornerPercent = 5
        )
        Spacer(modifier = Modifier.height(8.dp))
        DownloadButton(
            isPressed = remember { mutableStateOf(true) },
            modifier = Modifier.fillMaxWidth(),
            cornerPercent = 5
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = movie.overview,
            fontSize = 13.sp,
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Light,
            maxLines = 4,
            lineHeight = 18.sp,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Average Vote: ",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = movie.avgVote.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ImageButton(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                icon = Icons.Default.Check,
                text = "My List"
            )
            ImageButton(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                icon = Icons.Outlined.ThumbUp,
                text = "Rate"
            )
            ImageButton(
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                icon = Icons.Default.Share,
                text = "Share"
            )
        }
    }
}


@Composable
fun MovieBanner(videoUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .background(Color.Black)
    ) {
        if (videoUrl.isNotEmpty()) {
            RoundedCornerRemoteImage(
                imageUrl = videoUrl,
                modifier = Modifier
                    .fillMaxSize(),
                cornerPercent = 0
            )
        }
    }
}

@Composable
fun ImageButton(
    modifier: Modifier,
    icon: ImageVector,
    text: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.selectable(selected = false, onClick = {})
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
    }
}