package com.example.cookit.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.models.onboardingPages
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun OnboardingScreen() {
    val context = LocalContext.current

    val onboardingPages = remember { onboardingPages }

    var currentPageIndex by remember { mutableStateOf(0) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                if (currentPageIndex < onboardingPages.size - 1) {
                    TextButton(
                        onClick = { navigateToNextScreen(context = context) },
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
                        onClick = { currentPageIndex++ },
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
                        onClick = { navigateToNextScreen(context = context) },
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
                    .fillMaxSize()
                    .blur(10.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(onboardingPages[currentPageIndex].titleResId),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = stringResource(onboardingPages[currentPageIndex].descriptionResId),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }
    }
}

private fun navigateToNextScreen(context: Context) {
    Toast.makeText(
        context,
        "Finish onboarding process",
        Toast.LENGTH_SHORT
    ).show()
}

@Preview
@Composable
fun OnboardingPagePreview() {
    CookItTheme {
        OnboardingScreen()
    }
}