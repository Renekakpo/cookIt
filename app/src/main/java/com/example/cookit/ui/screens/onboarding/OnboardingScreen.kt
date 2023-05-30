package com.example.cookit.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cookit.R
import com.example.cookit.models.onboardingPages
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.screens.auth.LoginRegistrationScreen
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider

object OnboardingScreen : NavDestination {
    override val route: String = "onboarding_screen"
}

@Composable
fun OnboardingScreen(
    navController: NavController,
    onboardingViewModel: OnboardingViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val onboardingPages = remember { onboardingPages }
    var currentPageIndex by remember { mutableStateOf(0) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                if (currentPageIndex < onboardingPages.size - 1) {
                    TextButton(
                        onClick = {
                            navigateToNextScreen(
                                navController = navController,
                                viewModel = onboardingViewModel
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = stringResource(R.string.skip_button_text),
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                    Button(
                        onClick = { currentPageIndex = currentPageIndex.inc() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = stringResource(R.string.next_button_text))
                        Icon(imageVector = Icons.Filled.KeyboardArrowRight, null)
                    }
                } else {
                    Button(
                        onClick = {
                            navigateToNextScreen(
                                navController = navController,
                                viewModel = onboardingViewModel
                            )
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = stringResource(R.string.get_started_button_text))
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(onboardingPages[currentPageIndex].imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
            ) {}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(onboardingPages[currentPageIndex].titleResId),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = stringResource(onboardingPages[currentPageIndex].descriptionResId),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

private fun navigateToNextScreen(navController: NavController, viewModel: OnboardingViewModel) {
    viewModel.saveOnboardingCompletionState(isCompleted = true)
    navController.navigate(route = LoginRegistrationScreen.route)
}

@Preview
@Composable
fun OnboardingPagePreview() {
    CookItTheme {
        OnboardingScreen(navController = rememberNavController())
    }
}