package com.codefylabs.netflix.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.codefylabs.netflix.ui.features.coming_soon.ComingSoonScreen
import com.codefylabs.netflix.ui.features.downloads.DownloadsScreen
import com.codefylabs.netflix.ui.features.home.HomeScreen
import com.codefylabs.netflix.ui.features.movie_detail.MovieDetailScreen
import com.codefylabs.netflix.ui.features.play_something.PlaySomethingScreen
import com.codefylabs.netflix.ui.navigation.MainDestinations.DASHBOARD_ROUTE
import com.codefylabs.netflix.ui.navigation.MainDestinations.MOVIE_ID_KEY
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf


object MainDestinations {
    const val DASHBOARD_ROUTE = "dashboard"
    const val MOVIE_DETAIL_ROUTE = "movieDetail"
    const val MOVIE_ID_KEY = "movieId"
}

@ExperimentalAnimationApi
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    mainActions: MainActions = MainActions(navController),
    startDestination: String = DASHBOARD_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {

        navigation(
            route = DASHBOARD_ROUTE,
            startDestination = DashboardSections.HOME.route
        ) {
            composable(DashboardSections.HOME.route) {
                HomeScreen(navigateToMovieDetail = mainActions.openMovieDetails)
            }
            composable(DashboardSections.PLAY_SOMETHING.route) {
                PlaySomethingScreen()
            }
            composable(DashboardSections.COMING_SOON.route) {
                ComingSoonScreen()
            }
            composable(DashboardSections.DOWNLOADS.route) {
                DownloadsScreen()
            }
        }

        composable(
            route = "${MainDestinations.MOVIE_DETAIL_ROUTE}/{$MOVIE_ID_KEY}",
            arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.LongType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
        ) { from: NavBackStackEntry ->
            val arguments = requireNotNull(from.arguments)
            val movieId = arguments.getLong(MOVIE_ID_KEY)

            MovieDetailScreen(upPress = {
                navController.navigateUp()
            }, viewModel = koinInject(parameters = { parametersOf(movieId) }))
        }
    }
}


class MainActions(
    navController: NavHostController,
) {
    val openMovieDetails = { movieId: Long ->
        navController.navigate("${MainDestinations.MOVIE_DETAIL_ROUTE}/$movieId") {
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
fun NavBackStackEntry.lifecycleIsResumed() = this.lifecycle.currentState == Lifecycle.State.RESUMED

