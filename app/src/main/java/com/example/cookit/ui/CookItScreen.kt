package com.example.cookit.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookit.R
import com.example.cookit.ui.screens.FavoriteScreen
import com.example.cookit.ui.screens.HomeScreen
import com.example.cookit.ui.screens.SearchScreen
import com.example.cookit.ui.screens.SettingsScreen
import com.example.cookit.ui.theme.CookItTheme

sealed class Screen(val route: String, val label: String, val iconID: Int) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Search : Screen("search", "Search", R.drawable.ic_search)
    object Favorite : Screen("favorite", "Favorite", R.drawable.ic_favorite)
    object Settings : Screen("settings", "Settings", R.drawable.ic_settings)
}

@Composable
fun CookItApp(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val screens = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Favorite,
        Screen.Settings
    )

    val currentRoute = currentRoute(navHostController)

    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = modifier
                    .padding(7.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(25.dp),
                        clip = false,
                        ambientColor = MaterialTheme.colors.secondary,
                        spotColor = MaterialTheme.colors.secondary,
                    )
                    .clip(RoundedCornerShape(25.dp)),
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
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Search.route) {
                SearchScreen()
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }

}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun CookItAppBar(modifier: Modifier = Modifier) {

}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CookItAppPreview() {
    CookItTheme {
        CookItApp(navHostController = rememberNavController())
    }
}