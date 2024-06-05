package com.codefylabs.netflix.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*

private val LightColorScheme = lightColorScheme(
    primary = netflix_light_primary,
    onPrimary = netflix_light_onPrimary,
    primaryContainer = netflix_light_primaryContainer,
    onPrimaryContainer = netflix_light_onPrimaryContainer,
    secondary = netflix_light_secondary,
    onSecondary = netflix_light_onSecondary,
    secondaryContainer = netflix_light_secondaryContainer,
    onSecondaryContainer = netflix_light_onSecondaryContainer,
    tertiary = netflix_light_tertiary,
    onTertiary = netflix_light_onTertiary,
    background = netflix_light_background,
    onBackground = netflix_light_onBackground,
    surface = netflix_light_surface,
    onSurface = netflix_light_onSurface,
    error = netflix_light_error,
    onError = netflix_light_onError,
)

private val DarkColorScheme = darkColorScheme(
    primary = netflix_dark_primary,
    onPrimary = netflix_dark_onPrimary,
    primaryContainer = netflix_dark_primaryContainer,
    onPrimaryContainer = netflix_dark_onPrimaryContainer,
    secondary = netflix_dark_secondary,
    onSecondary = netflix_dark_onSecondary,
    secondaryContainer = netflix_dark_secondaryContainer,
    onSecondaryContainer = netflix_dark_onSecondaryContainer,
    tertiary = netflix_dark_tertiary,
    onTertiary = netflix_dark_onTertiary,
    background = netflix_dark_background,
    onBackground = netflix_dark_onBackground,
    surface = netflix_dark_surface,
    onSurface = netflix_dark_onSurface,
    error = netflix_dark_error,
    onError = netflix_dark_onError,
)


internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun AppTheme(
    content: @Composable() () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            typography = NetflixTypography(),
            content = { Surface(content = content) }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
