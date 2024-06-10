package com.codefylabs.netflix.ui.features.movie_detail.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codefylabs.netflix.ui.common.NetflixSurface
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailAppBar(
    modifier: Modifier = Modifier,
    upPress: () -> Unit
) {
    NetflixSurface(
        modifier = modifier
            .padding(top = 30.dp)
            .height(70.dp),
        color = Color.Black.copy(alpha = 0.5f)
    ) {
        TopAppBar(
            modifier = modifier
                .height(80.dp),
            navigationIcon = {
                IconButton(onClick = upPress){
                    Icon(imageVector = FeatherIcons.ArrowLeft, contentDescription = null)
                }
            },
            title = {

            }
        )
    }
}