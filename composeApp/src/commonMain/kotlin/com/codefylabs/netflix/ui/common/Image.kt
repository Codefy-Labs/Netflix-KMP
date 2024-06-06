package com.codefylabs.netflix.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem

@Composable
fun EnhancedSubcomposeAsyncImage(
    model: Any?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    imageLoader: ImageLoader = getAsyncImageLoader(LocalPlatformContext.current),
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        modifier = modifier,
        contentScale = contentScale,
        loading = {
            ShimmerEffect(
                modifier = Modifier.fillMaxSize()
            )
        },
        success = { state ->
            Image(
                painter = state.painter,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = contentScale
            )
        },
        error = {
            // Handle the error state here if needed
        }
    )
}

@Composable
fun CircularRemoteImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    NetflixSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        EnhancedSubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun CircularLocalImage(
    resId: Int,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    NetflixSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        EnhancedSubcomposeAsyncImage(
            model = resId,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun RoundedCornerRemoteImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    cornerPercent: Int,
    elevation: Dp = 0.dp,
) {
    NetflixSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = RoundedCornerShape(cornerPercent),
        modifier = modifier
    ) {
        EnhancedSubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun FullScreenRemoteImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    NetflixSurface(
        color = Color.LightGray,
        elevation = 0.dp,
        shape = RectangleShape,
        modifier = modifier
    ) {
        EnhancedSubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}


fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED).diskCache {
            newDiskCache()
        }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(512L * 1024 * 1024)
        .build()
}