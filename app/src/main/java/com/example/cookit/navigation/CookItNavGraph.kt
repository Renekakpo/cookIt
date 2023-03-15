package com.example.cookit.navigation

import com.example.cookit.ui.screens.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookit.R
import com.example.cookit.models.recipe
import com.example.cookit.ui.screens.auth.LoginRegistrationScreen
import com.example.cookit.ui.screens.onboarding.OnboardingScreen
import com.example.cookit.ui.screens.recipeItem.RecipeDetailScreen
import com.example.cookit.ui.screens.recipeItem.RecipeDetailsScreen


sealed class BottomNavScreen(val route: String, val label: String, val iconID: Int) {
    object Home : BottomNavScreen("home", "Home", R.drawable.ic_home)
    object Search : BottomNavScreen("search", "Search", R.drawable.ic_search)
    object Favorite : BottomNavScreen("favorite", "Favorite", R.drawable.ic_favorite)
    object Settings : BottomNavScreen("settings", "Settings", R.drawable.ic_settings)
}

@Composable
fun CookItMainNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {
        composable(route = SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(route = LoginRegistrationScreen.route) {
            LoginRegistrationScreen(navController = navController)
        }
        composable(route = BottomNavGraph.route) {
            CookItBottomNavHost(navController = rememberNavController())
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