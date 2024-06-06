package com.codefylabs.netflix.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codefylabs.netflix.models.Movie

@Composable
fun LargeMovieItem(
    movie: Movie,
    onMovieSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (movieImage, topTrendingBanner) = createRefs()

        RoundedCornerRemoteImage(
            imageUrl = movie.posterUrl,
            modifier = modifier
                .width(170.dp)
                .height(320.dp)
                .clickable(onClick = {
                    onMovieSelected(movie.id)
                })
                .constrainAs(movieImage) {
                    top.linkTo(parent.top)
                },
            cornerPercent = 3
        )
        if (movie.avgVote >= 8) {
            TopTrendingBanner(
                modifier = modifier.constrainAs(topTrendingBanner) {
                    end.linkTo(movieImage.end)
                },
                width = 25.dp,
                height = 32.dp,
                fontSizeTitle = 8.sp,
                fontSizeSubtitle = 14.sp
            )
        }
    }
}

@Composable
fun SmallMovieItem(
    movie: Movie,
    onMovieSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (movieImage, topTrendingBanner) = createRefs()
        RoundedCornerRemoteImage(
            imageUrl = movie.posterUrl,
            modifier = modifier
                .width(110.dp)
                .height(150.dp)
                .clickable(onClick = {
                    onMovieSelected(movie.id)
                })
                .constrainAs(movieImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            cornerPercent = 3
        )
        if (movie.avgVote >= 8) {
            TopTrendingBanner(
                modifier = modifier.constrainAs(topTrendingBanner) {
                    top.linkTo(movieImage.top)
                    end.linkTo(movieImage.end)
                },
                width = 20.dp,
                height = 28.dp,
                fontSizeTitle = 6.sp,
                fontSizeSubtitle = 12.sp
            )
        }
    }
}

@Composable
fun TopTrendingBanner(
    modifier: Modifier,
    width: Dp,
    height: Dp,
    fontSizeTitle: TextUnit,
    fontSizeSubtitle: TextUnit
) {
    Card(
        shape = RoundedCornerShape(10),
        modifier = modifier
            .size(
                width = width,
                height = height
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                    fontSize = fontSizeTitle,
                    lineHeight = 0.sp
                ),
                maxLines = 1
            )
            Text(
                text = "10",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = fontSizeSubtitle,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 0.sp
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun HighlightMovieItem(
    movie: Movie,
    onMovieSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    FullScreenRemoteImage(
        imageUrl = movie.posterUrl,
        modifier = modifier
            .fillMaxWidth()
            .height(550.dp)
            .clickable(onClick = {
                onMovieSelected(movie.id)
            })
            .applyGradient()
    )
}

fun Modifier.applyGradient(): Modifier {
    return drawWithCache {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = size.height / 3,
            endY = size.height
        )
        onDrawWithContent {
            drawContent()
            drawRect(gradient, blendMode = BlendMode.Multiply)
        }
    }
}