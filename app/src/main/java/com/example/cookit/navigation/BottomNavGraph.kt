package com.example.cookit.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cookit.ui.common.FilterScreen
import com.example.cookit.ui.screens.SettingsScreen
import com.example.cookit.ui.screens.favorite.FavoriteScreen
import com.example.cookit.ui.screens.home.HomeScreen
import com.example.cookit.ui.screens.search.SearchScreen
import com.example.cookit.ui.screens.search.SearchViewModel
import com.example.cookit.utils.AppViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object BottomNavGraph : NavDestination {
    override val route: String = "bottom_nav"
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CookItBottomNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Favorite,
        BottomNavScreen.Settings
    )
    val currentRoute = homeNavHostCurrentRoute(navController)
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
            modalSheetState = modalSheetState,
            coroutineScope = coroutineScope
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    screens: List<BottomNavScreen>,
    currentRoute: String?,
    navController: NavHostController,
    modalSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
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
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
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
            navController = navController,
            startDestination = BottomNavScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavScreen.Home.route) {
                HomeScreen()
            }
            composable(route = BottomNavScreen.Search.route) {
                SearchScreen(modalSheetState = modalSheetState, coroutineScope = coroutineScope)
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