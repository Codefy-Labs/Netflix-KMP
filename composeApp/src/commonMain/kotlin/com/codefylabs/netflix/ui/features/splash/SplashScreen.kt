package com.codefylabs.netflix.ui.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codefylabs.netflix.ui.common.logo.NetflixLogo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        var drawLogo: Boolean by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(1.seconds)
            drawLogo = true

            launch {
                delay(3000)
                onFinished()
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                if (drawLogo) {
                    NetflixLogo(
                        animated = true,
                        modifier = Modifier.width(150.dp)
                    )
                }
            }
        }
    }
}