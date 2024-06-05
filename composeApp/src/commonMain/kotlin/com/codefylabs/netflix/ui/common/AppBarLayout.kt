package com.codefylabs.netflix.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import netflix_kmp.composeapp.generated.resources.Res
import netflix_kmp.composeapp.generated.resources.ic_netflix_logo
import netflix_kmp.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource


private val TopBarHeight = 80.dp
private val MovieDetailTopBarHeight = 70.dp

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun NetflixTopAppBar(
    isScrolledDown: Boolean,
    modifier: Modifier = Modifier,
) {
    NetflixSurface(
        modifier = modifier
            .padding(top = getTopBarWidthState(isScrolledDown = isScrolledDown).value)
            .height(TopBarHeight),
        color = getTopBarColorState(isScrolledDown = isScrolledDown).value
    ) {
        TopAppBar(
            modifier = modifier
                .height(TopBarHeight),
            title = {
                Column {
                    AnimatedVisibility(visible = isScrolledDown.not()) {
                        AppBar()
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    MenuBar()
                }
            }
        )
    }
}

@Composable
fun AppBar(
    showBack: Boolean = false,
    upPress: () -> Unit = {}
) {
    Row(
        modifier = Modifier.padding(start = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(6f)
                .align(Alignment.CenterVertically)
        ) {
            if (showBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { upPress() }
                )
            } else {
                Image(
                    painterResource(Res.drawable.ic_netflix_logo),
                    contentDescription = "Netflix logo",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { }
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clickable { }
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clickable { }
        ) {
            Image(
                painterResource(Res.drawable.ic_profile),
                contentDescription = "User profile",
                modifier = Modifier
                    .size(25.dp)
            )
        }
    }
}

@Composable
private fun MenuBar() {
    Row {
        TopAppBarMenuItem(
            text = "TV Shows",
            modifier = Modifier.weight(1f)
        )
        TopAppBarMenuItem(
            text = "Movies",
            modifier = Modifier.weight(1f)
        )
        TopAppBarMenuItem(
            text = "Categories",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TopAppBarMenuItem(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            shadow = Shadow(color = Color.Black, blurRadius = 10f),
            fontSize = 16.sp
        ),
        modifier = modifier
            .padding(5.dp)
            .clickable { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun MovieDetailAppBar(
    modifier: Modifier = Modifier,
    upPress: () -> Unit
) {
    NetflixSurface(
        modifier = modifier
            .padding(top = 30.dp)
            .height(MovieDetailTopBarHeight),
        color = Color.Black.copy(alpha = 0.5f)
    ) {
        TopAppBar(
            modifier = modifier
                .height(TopBarHeight),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                Column {
                    AppBar(showBack = true, upPress = upPress)
                }
            }
        )
    }
}