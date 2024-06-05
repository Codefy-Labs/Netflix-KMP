package com.codefylabs.netflix.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
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


object MainDestinations {
    const val DASHBOARD_ROUTE = "dashboard"
    const val MOVIE_DETAIL_ROUTE = "movieDetail"
    const val MOVIE_ID_KEY = "movieId"
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = DASHBOARD_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigation(
            route = DASHBOARD_ROUTE,
            startDestination = DashboardSections.HOME.route
        ) {
            composable(DashboardSections.HOME.route) {
                HomeScreen()
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
            arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.LongType })
        ) { from: NavBackStackEntry ->
            val arguments = requireNotNull(from.arguments)
            val movieId = arguments.getLong(MOVIE_ID_KEY)

            MovieDetailScreen()
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(
    navController: NavHostController,
    updateAppBarVisibility: (Boolean) -> Unit
) {
    val openMovieDetails = { movieId: Long ->
        updateAppBarVisibility(false)
        navController.navigate("${MainDestinations.MOVIE_DETAIL_ROUTE}/$movieId") {
            // Pop up to the start destination of the graph to avoid building up a large
            // stack of destinations on the back stack as users select items
            popUpTo(navController.graph.startDestinationRoute ?: DASHBOARD_ROUTE)
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
        }
    }
    val upPress: (rom: NavBackStackEntry) -> Unit = { from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            updateAppBarVisibility(true)
            navController.navigateUp()
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
fun NavBackStackEntry.lifecycleIsResumed() = this.lifecycle.currentState == Lifecycle.State.RESUMED
