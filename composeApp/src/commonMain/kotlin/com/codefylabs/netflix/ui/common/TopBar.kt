package com.codefylabs.netflix.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getTopBarWidthState(isScrolledDown: Boolean): State<Dp> {
  return animateDpAsState(
    targetValue = if (isScrolledDown) 0.dp else 50.dp,
  )
}

@Composable
fun getTopBarColorState(isScrolledDown: Boolean): State<Color> {
  return animateColorAsState(
    targetValue = if (isScrolledDown) Color.Black.copy(alpha = 0.5f) else Color.Transparent,
  )
}