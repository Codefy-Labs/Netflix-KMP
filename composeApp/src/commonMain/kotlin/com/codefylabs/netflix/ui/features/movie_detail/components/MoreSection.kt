package com.codefylabs.netflix.ui.features.movie_detail.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codefylabs.netflix.models.Movie


@Composable
fun MoreMovies(similarMovies: List<Movie>) {
    val (currentCategory, setCurrentCategory) = rememberSaveable { mutableStateOf(MoreMoviesCategory.MoreLikeThis) }
    MoreMoviesTabs(
        selectedCategory = currentCategory,
        onCategorySelected = setCurrentCategory,
        modifier = Modifier.fillMaxWidth()
    )
    val tweenSpec = remember { getAnimSpec() }
    Crossfade(
        currentCategory,
        animationSpec = tweenSpec,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) { category ->
        when (category) {
            MoreMoviesCategory.MoreLikeThis -> MoreLikeThis(similarMovies = similarMovies)
            MoreMoviesCategory.TrailersAndMore -> TrailersAndMore()
        }
    }
}

private fun getAnimSpec(): TweenSpec<Float> {
    return TweenSpec(
        durationMillis = 600,
        easing = LinearOutSlowInEasing
    )
}


enum class MoreMoviesCategory {
    MoreLikeThis,
    TrailersAndMore
}

@Composable
fun MoreMoviesTabs(
    modifier: Modifier = Modifier,
    categories: List<MoreMoviesCategory> = listOf(
        MoreMoviesCategory.MoreLikeThis, MoreMoviesCategory.TrailersAndMore
    ),
    selectedCategory: MoreMoviesCategory,
    onCategorySelected: (MoreMoviesCategory) -> Unit,
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        HomeCategoryTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }

    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp
        )
        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            indicator = indicator,
            divider = {},
            edgePadding = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEachIndexed { index, category ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = { onCategorySelected(category) },
                    modifier = modifier,
                    text = {
                        Text(
                            text = when (category) {
                                MoreMoviesCategory.MoreLikeThis -> "More like this"
                                MoreMoviesCategory.TrailersAndMore -> "Trailers and more"
                            },
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (index == selectedIndex) FontWeight.Bold
                                else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun HomeCategoryTabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
) {
    Spacer(
        modifier
            .width(5.dp)
            .height(3.dp)
            .background(color, RoundedCornerShape(percent = 50))
    )
}

