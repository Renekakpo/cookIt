package com.example.cookit

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookit.ui.CookItApp
import com.example.cookit.ui.screens.splash.SplashScreen
import com.example.cookit.ui.theme.CookItTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CookItUITests {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun splashScreen_shouldNavigateToCorrectRoute() {
        var navController: NavHostController? = null
        composeTestRule.setContent {
            navController = rememberNavController()

            NavHost(navController = navController!!, startDestination = SplashScreen.route) {
                composable(route = SplashScreen.route) {
                    CookItTheme {
                        SplashScreen(navController = navController!!)
                    }
                }
                // Define other destinations
            }
        }

        // Assert that the SplashScreen displays the app name
        val appName = composeTestRule.activity.getString(R.string.app_name)
        composeTestRule.onNodeWithText(appName).assertIsDisplayed()

        // Simulate a delay of 2000ms
        Thread.sleep(2000)

        // Assert that the navigation was performed correctly
        val currentDestination = composeTestRule.runOnIdle { navController?.currentBackStackEntry?.destination }
        assert(currentDestination?.route == SplashScreen.route)
    }
}