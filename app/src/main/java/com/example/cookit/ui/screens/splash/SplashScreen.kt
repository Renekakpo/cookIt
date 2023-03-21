package com.example.cookit.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookit.R
import com.example.cookit.navigation.BottomNavGraph
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.screens.auth.LoginRegistrationScreen
import com.example.cookit.ui.screens.onboarding.OnboardingScreen
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

object SplashScreen : NavDestination {
    override val route: String = "splash_screen"
}

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var route by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        val splashUiState = splashViewModel.uiState.first() // Get the latest state
        route = if (!splashUiState.isOnboardingCompleted) {
            OnboardingScreen.route
        } else if (!splashUiState.isLoginCompleted) {
            LoginRegistrationScreen.route
        } else {
            BottomNavGraph.route
        }
        delay(timeMillis = 2000)
        navigateToNextScreen(navController = navController, route = route)
    }

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

private fun navigateToNextScreen(navController: NavHostController, route: String) {
    navController.navigate(route = route)
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    CookItTheme {
        SplashScreen(navController = rememberNavController())
    }
}