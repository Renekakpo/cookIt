package com.example.cookit.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.theme.CookItTheme
import kotlinx.coroutines.delay

object SplashScreen : NavDestination {
    override val route: String = "splash_screen"
}

@Composable
fun SplashScreen(
    navigateToAuthScreen: () -> Unit,
    navigateToOnboardingScreen: () -> Unit,
    navigationToBottomNavScreen: () -> Unit
) {
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

    LaunchedEffect(true) {
        delay(5000)
        navigateToOnboardingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    CookItTheme {
        SplashScreen(
            navigateToAuthScreen = {},
            navigateToOnboardingScreen = {},
            navigationToBottomNavScreen = {}
        )
    }
}