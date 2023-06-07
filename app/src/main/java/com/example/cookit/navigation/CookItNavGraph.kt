package com.example.cookit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cookit.ui.screens.auth.LoginRegistrationScreen
import com.example.cookit.ui.screens.onboarding.OnboardingScreen
import com.example.cookit.ui.screens.recipeItem.RecipeDetailScreen
import com.example.cookit.ui.screens.recipeItem.RecipeDetailsScreenMainContainer
import com.example.cookit.ui.screens.splash.SplashScreen

@Composable
fun CookItMainNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {
        composable(route = SplashScreen.route) {
            SplashScreen(navController = navHostController)
        }
        composable(route = OnboardingScreen.route) {
            OnboardingScreen(navController = navHostController)
        }
        composable(route = LoginRegistrationScreen.route) {
            LoginRegistrationScreen(navController = navHostController)
        }
        composable(route = BottomNavGraph.route) {
            CookItBottomNavHost(navController = navHostController)
        }
        composable(
            route = "${RecipeDetailScreen.route}/{itemId}",
            arguments = listOf(navArgument(name = "itemId") {
                type = NavType.LongType
            })
        ) { navBackStackEntry ->
            val itemId = navBackStackEntry.arguments?.getLong("itemId")
            if (itemId != null) {
                RecipeDetailsScreenMainContainer(
                    id = itemId,
                    onBackClicked = { navHostController.popBackStack() },
                    navigateUp = { navHostController.navigateUp() }
                )
            } else {
                // Navigate back
                navHostController.popBackStack()
            }
        }
    }
}