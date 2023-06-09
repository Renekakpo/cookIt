package com.example.cookit.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cookit.R
import com.example.cookit.ui.common.FilterScreen
import com.example.cookit.ui.screens.favorite.FavoriteScreen
import com.example.cookit.ui.screens.home.HomeItemScreen
import com.example.cookit.ui.screens.recipeItem.RecipeDetailScreen
import com.example.cookit.ui.screens.search.SearchScreen
import com.example.cookit.ui.screens.search.SearchViewModel
import com.example.cookit.ui.screens.settings.SettingsItemScreen
import com.example.cookit.utils.AppViewModelProvider
import kotlinx.coroutines.launch

object BottomNavGraph : NavDestination {
    override val route: String = "bottom_nav"
}

sealed class BottomNavScreen(val route: String, val label: String, val iconID: Int) {
    object Home : BottomNavScreen("home", "Home", R.drawable.ic_home)
    object Search : BottomNavScreen("search", "Search", R.drawable.ic_search)
    object Favorite : BottomNavScreen("favorite", "Favorite", R.drawable.ic_favorite)
    object Settings : BottomNavScreen("settings", "Settings", R.drawable.ic_settings)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CookItBottomNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Favorite,
        BottomNavScreen.Settings
    )

    val currentRoute = remember { mutableStateOf(BottomNavScreen.Home.route) }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    val searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 25.dp,
            topEnd = 25.dp
        ),
        sheetContent = {
            FilterScreen(
                searchViewModel = searchViewModel,
                closeBottomSheet = {
                    coroutineScope.launch {
                        if (modalSheetState.isVisible) {
                            modalSheetState.hide()
                        }
                    }
                }
            )
        },
    ) {
        BottomNav(
            modifier = modifier,
            screens = screens,
            currentRoute = currentRoute,
            navController = navController,
            onFilterClicked = { coroutineScope.launch { modalSheetState.show() } }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    screens: List<BottomNavScreen>,
    currentRoute: MutableState<String>,
    onFilterClicked: () -> Unit,
    navController: NavHostController
) {

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
                    val selected = currentRoute.value == screen.route
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
                        onClick = { currentRoute.value = screen.route }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Content of the selected screen
        when (currentRoute.value) {
            BottomNavScreen.Home.route -> {
                HomeItemScreen(
                    modifier = Modifier.padding(innerPadding),
                    onItemSelected = { recipeId ->
                        RecipeDetailScreen.itemID = recipeId
                        val destination =
                            "${RecipeDetailScreen.route}/${RecipeDetailScreen.itemID}"
                        navController.navigate(route = destination) {
                            popUpTo(route = BottomNavGraph.route) {
                                saveState = currentRoute.value == BottomNavScreen.Home.route
                            }
                            launchSingleTop = true
                        }
                    })
            }
            BottomNavScreen.Search.route -> {
                SearchScreen(
                    modifier = Modifier.padding(innerPadding),
                    onFilterClicked = onFilterClicked,
                    onRecipeSelected = { recipeId ->
                        RecipeDetailScreen.itemID = recipeId
                        val destination =
                            "${RecipeDetailScreen.route}/${RecipeDetailScreen.itemID}"
                        navController.navigate(route = destination) {
                            popUpTo(route = BottomNavGraph.route) {
                                saveState = currentRoute.value == BottomNavScreen.Home.route
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            BottomNavScreen.Favorite.route -> {
                FavoriteScreen(
                    modifier = Modifier.padding(innerPadding),
                    onRecipeSelected = { recipeId ->
                        RecipeDetailScreen.itemID = recipeId
                        val destination =
                            "${RecipeDetailScreen.route}/${RecipeDetailScreen.itemID}"
                        navController.navigate(route = destination) {
                            popUpTo(route = BottomNavGraph.route) {
                                saveState = currentRoute.value == BottomNavScreen.Home.route
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            BottomNavScreen.Settings.route -> {
                SettingsItemScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}