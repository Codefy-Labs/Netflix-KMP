package com.codefylabs.netflix.ui.features.coming_soon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import netflix_kmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ComingSoonScreen() {
    var bytes by remember {
        mutableStateOf(ByteArray(0))
    }
    LaunchedEffect(Unit) {
        bytes = Res.readBytes("files/coming_soon.json")
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.JsonString(
            bytes.decodeToString()
        )
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center),
                restartOnPlay = true,
                iterations = Int.MAX_VALUE
            )
        }
    }
}