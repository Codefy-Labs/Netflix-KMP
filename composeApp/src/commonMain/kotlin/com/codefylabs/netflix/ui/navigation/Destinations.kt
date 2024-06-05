package com.codefylabs.netflix.ui.navigation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import compose.icons.FeatherIcons
import compose.icons.feathericons.Download
import compose.icons.feathericons.PlayCircle
import compose.icons.feathericons.Shuffle
import org.jetbrains.compose.resources.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class DashboardSections(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("Home", Icons.Default.Home, "dashboard/home"),
    PLAY_SOMETHING("Play Something", FeatherIcons.Shuffle, "dashboard/play"),
    COMING_SOON("Coming Soon", FeatherIcons.PlayCircle, "dashboard/coming_soon"),
    DOWNLOADS("Downloads", FeatherIcons.Download, "dashboard/downloads")
}

@Composable
fun NetflixBottomBar(
    navController: NavController,
    tabs: Array<DashboardSections>,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val dashboardSections = remember { DashboardSections.entries.toTypedArray() }
    val routes = remember { dashboardSections.map { it.route } }

    if (currentRoute in routes) {
        val currentSection = dashboardSections.first { it.route == currentRoute }

        Surface {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp, top = 14.dp)
            ) {
                tabs.forEach { section ->
                    val selected = section == currentSection

                    NetflixBottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = section.icon,
                                contentDescription = null,
                                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        text = {
                            Text(
                                text = section.title,
                                style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
                                maxLines = 1,
                                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        selected = selected,
                        onSelected = {
                            if (section.route != currentRoute) {
                                navController.navigate(section.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(
                                        findStartDestination(navController.graph).route
                                            ?: DashboardSections.HOME.route
                                    ) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
    }
}

private val NavGraph.startDestination: NavDestination?
    get() = findStartDestination()


private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

@Composable
fun NetflixBottomNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.selectable(
            selected = selected,
            onClick = onSelected,
            interactionSource = MutableInteractionSource(),
            indication = null
        ),
    ) {
        Box(
            modifier = Modifier.layoutId("icon"),
            content = icon
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier.layoutId("text"),
            content = text
        )
    }
}

