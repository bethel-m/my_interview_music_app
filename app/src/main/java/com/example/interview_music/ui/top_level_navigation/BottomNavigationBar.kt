package com.example.interview_music.ui.top_level_navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    BottomAppBar {
        TopLevelDestinations.destinations.forEach { destination ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true

            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(
                        text = stringResource(destination.title),
                        style = if (isSelected) {
                            MaterialTheme.typography.labelLarge
                        } else {
                            LocalTextStyle.current
                        },
                        // color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                    )
                },
                onClick = {
                    navController.navigate(destination.route) {

                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(
                            id = navController.graph.findStartDestination().id,
                        ) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }

                },
                icon = {
                    Icon(
                        painter = painterResource(id = if (isSelected) destination.selectedIcon else destination.unselectedIcon),
                        contentDescription = stringResource(destination.iconDescription),
                    )
                }
            )


        }
    }
}
