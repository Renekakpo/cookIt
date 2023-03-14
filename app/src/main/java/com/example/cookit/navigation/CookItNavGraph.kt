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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.cookit.R
import com.example.cookit.models.recipe
import com.example.cookit.ui.screens.*


sealed class BottomNavScreen(val route: String, val label: String, val iconID: Int) {
    object Home : BottomNavScreen("home", "Home", R.drawable.ic_home)
    object Search : BottomNavScreen("search", "Search", R.drawable.ic_search)
    object Favorite : BottomNavScreen("favorite", "Favorite", R.drawable.ic_favorite)
    object Settings : BottomNavScreen("settings", "Settings", R.drawable.ic_settings)
}

@Composable
fun CookItMainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {
        composable(route = SplashScreen.route) {
            SplashScreen(
                navigateToAuthScreen = { navController.navigate(route = LoginRegistrationScreen.route) },
                navigateToOnboardingScreen = { navController.navigate(route = OnboardingScreen.route) },
                navigationToBottomNavScreen = { navController.navigate(route = BottomNavGraph.route) }
            )
        }
        composable(route = OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(route = LoginRegistrationScreen.route) {
            LoginRegistrationScreen(navController = navController)
        }
        composable(route = BottomNavGraph.route) {
            CookItBottomNavHost(
                navHostController = rememberNavController()
            )
        }
        composable(route = RecipeDetailScreen.route) {
            RecipeDetailsScreen(
                recipe = recipe,
                onBackClicked = { navController.popBackStack() },
                onLikeClicked = { },
                onStartCookingClicked = { },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}