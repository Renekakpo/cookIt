package com.example.cookit.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cookit.ui.screens.FavoriteScreen
import com.example.cookit.ui.screens.HomeScreen
import com.example.cookit.ui.screens.SearchScreen
import com.example.cookit.ui.screens.SettingsScreen

object BottomNavGraph : NavDestination {
    override val route: String = "bottom_nav"
}

@Composable
fun CookItBottomNavHost(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Favorite,
        BottomNavScreen.Settings
    )

    val currentRoute = homeNavHostCurrentRoute(navHostController)

    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = modifier
                    .height(50.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                        clip = false,
                        ambientColor = MaterialTheme.colors.secondary,
                        spotColor = MaterialTheme.colors.secondary,
                    )
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)),
                backgroundColor = if (MaterialTheme.colors.isLight) Color.White else MaterialTheme.colors.primary,
                elevation = 4.dp,
            ) {
                screens.forEach { screen ->
                    val selected = currentRoute == screen.route
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = screen.iconID),
                                contentDescription = screen.label
                            )
                        },
                        label = { Text(text = screen.label) },
                        selected = selected,
                        alwaysShowLabel = false,
                        selectedContentColor = MaterialTheme.colors.primaryVariant,
                        unselectedContentColor = Color.LightGray,
                        onClick = {
                            navHostController.navigate(screen.route) {
                                popUpTo(navHostController.graph.startDestinationId) {
                                    saveState = currentRoute == screen.route
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = BottomNavScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavScreen.Home.route) {
                HomeScreen()
            }
            composable(route = BottomNavScreen.Search.route) {
                SearchScreen()
            }
            composable(route = BottomNavScreen.Favorite.route) {
                FavoriteScreen()
            }
            composable(route = BottomNavScreen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun homeNavHostCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}