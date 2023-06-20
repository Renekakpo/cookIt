package com.example.cookit.ui.screens.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cookit.R
import com.example.cookit.models.onboardingPages
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.screens.auth.LoginRegistrationScreen
import com.example.cookit.utils.AppViewModelProvider

object OnboardingScreen : NavDestination {
    override val route: String = "onboarding_screen"
}

@Composable
fun OnboardingScreen(
    navController: NavController,
    onBoardingViewModel: OnboardingViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val onBoardingPages = remember { onboardingPages }
    val pageCount = onBoardingPages.size
    var currentPageIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 45.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(imageRes = onBoardingPages[currentPageIndex].imageResId)

        DotPageIndicator(pageCount = pageCount, currentPageIndex = currentPageIndex)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = onBoardingPages[currentPageIndex].titleResId),
                style = MaterialTheme.typography.h4
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = onBoardingPages[currentPageIndex].descriptionResId),
                style = MaterialTheme.typography.body1
            )
        }

        val buttonText = if (currentPageIndex == pageCount - 1) {
            stringResource(R.string.get_started_button_text)
        } else {
            stringResource(R.string.next_button_text)
        }

        CircularButton(
            text = buttonText,
            onClick = {
                if (currentPageIndex < pageCount - 1) {
                    currentPageIndex++
                } else {
                    navigateToNextScreen(
                        navController = navController,
                        viewModel = onBoardingViewModel
                    )
                }
            }
        )
    }
}

@Composable
fun CircularImage(imageRes: Int) {
    Box(
        modifier = Modifier
            .size(350.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(350.dp)
        )
    }
}

@Composable
fun DotPageIndicator(pageCount: Int, currentPageIndex: Int) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { pageIndex ->
            val size by animateFloatAsState(
                targetValue = if (pageIndex == currentPageIndex) 12f else 8f,
                animationSpec = tween(durationMillis = 300)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(size.dp)
                    .clip(CircleShape)
                    .background(
                        if (pageIndex == currentPageIndex) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground.copy(
                            alpha = 0.3f
                        )
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun CircularButton(text: String, onClick: () -> Unit) {
    val shape = if (text == stringResource(id = R.string.next_button_text)) {
        CircleShape
    } else {
        MaterialTheme.shapes.small
    }

    Box(
        modifier = Modifier
            .clip(shape)
            .background(MaterialTheme.colors.primary)
            .padding(10.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        val arrowAlpha by animateFloatAsState(
            targetValue = if (text == stringResource(R.string.get_started_button_text)) 0f else 1f,
            animationSpec = tween(durationMillis = 600)
        )

        if (text == stringResource(id = R.string.next_button_text)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = stringResource(id = R.string.next_button_text),
                tint = MaterialTheme.colors.background,
                modifier = Modifier
                    .size(25.dp)
                    .alpha(arrowAlpha)
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .alpha(1f - arrowAlpha)
            )
        }
    }
}

private fun navigateToNextScreen(
    navController: NavController,
    viewModel: OnboardingViewModel
) {
    viewModel.saveOnboardingCompletionState(isCompleted = true)
    navController.navigate(route = LoginRegistrationScreen.route)
}