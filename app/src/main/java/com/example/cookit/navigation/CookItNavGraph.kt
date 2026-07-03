package com.example.cookit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cookit.ui.screens.recipeItem.RecipeDetailScreen
import com.example.cookit.ui.screens.recipeItem.RecipeDetailsScreenMainContainer

@Composable
fun CookItMainNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = BottomNavGraph.route,
        modifier = modifier
    ) {
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
                    navigateUp = { navHostController.navigateUp() }
                )
            } else {
                // Navigate back
                navHostController.popBackStack()
            }
        }
    }
}