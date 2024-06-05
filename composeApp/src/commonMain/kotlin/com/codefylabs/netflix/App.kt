package com.codefylabs.netflix

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.codefylabs.netflix.di.appModule
import com.codefylabs.netflix.theme.AppTheme
import com.codefylabs.netflix.ui.features.splash.SplashScreen
import com.codefylabs.netflix.ui.navigation.AppNavGraph
import com.codefylabs.netflix.ui.navigation.DashboardSections
import com.codefylabs.netflix.ui.navigation.NetflixBottomBar
import org.koin.core.context.KoinContext
import org.koin.compose.KoinApplication

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun App() = AppTheme {
    KoinApplication(application = {
        modules(appModule())
    }) {
        val navController = rememberNavController()
        val tabs = remember { DashboardSections.entries.toTypedArray() }
        var showSplash by rememberSaveable {
            mutableStateOf(true)
        }

        Box {
            AnimatedVisibility(
                !showSplash,
                modifier = Modifier.fillMaxSize(),
            ) {
                Scaffold(
                    bottomBar = {
                        NetflixBottomBar(
                            navController = navController,
                            tabs = tabs
                        )
                    }) { innerPaddingModifier ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPaddingModifier),
                    )
                }
            }

            AnimatedVisibility(
                showSplash,
                modifier = Modifier.fillMaxSize(),
                enter = fadeIn() + scaleIn(initialScale = 0.9f),
                exit = fadeOut() + scaleOut(targetScale = 4f),
            ) {
                SplashScreen {
                    showSplash = false
                }
            }
        }
    }
}

internal expect fun openUrl(url: String?)
