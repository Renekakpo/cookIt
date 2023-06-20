package com.example.cookit

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cookit.models.onboardingPages
import com.example.cookit.navigation.BottomNavGraph
import com.example.cookit.navigation.CookItBottomNavHost
import com.example.cookit.navigation.CookItMainNavHost
import com.example.cookit.ui.CookItApp
import com.example.cookit.ui.screens.auth.LoginRegistrationScreen
import com.example.cookit.ui.screens.onboarding.OnboardingScreen
import com.example.cookit.ui.screens.splash.SplashScreen
import com.example.cookit.ui.theme.CookItTheme
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CookItUITests {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun splashScreen_to_onboardingScreen_navigationTest() = runBlocking {
        var navController: NavHostController? = null

        composeTestRule.setContent {
            navController = rememberNavController()
            CookItMainNavHost(navHostController = navController!!)
        }

        // Assert that the SplashScreen displays the app name
        val appName = composeTestRule.activity.getString(R.string.app_name)
        composeTestRule.onNodeWithText(appName).assertIsDisplayed()

        // Simulate a delay of 3000ms
        Thread.sleep(2000)

        // Trigger the navigation to the next destination
        composeTestRule.runOnIdle {
            navController?.navigate(OnboardingScreen.route)
        }

        // Wait for the navigation to complete
        composeTestRule.waitForIdle()

        // Assert that the navigation to OnboardingScreen occurred
        val currentDestination = composeTestRule.runOnIdle {
            navController?.currentBackStackEntry?.destination?.route
        }
        assertEquals(OnboardingScreen.route, currentDestination)
    }

    @Test
    fun onboardingScreen_to_loginRegistrationScreen_navigationTest() {
        var navController: NavHostController? = null

        composeTestRule.setContent {
            navController = rememberNavController()

            NavHost(navController = navController!!, startDestination = OnboardingScreen.route) {
                composable(route = OnboardingScreen.route) {
                    CookItTheme {
                        OnboardingScreen(navController = navController!!)
                    }
                }
                composable(route = LoginRegistrationScreen.route) {
                    CookItTheme {
                        LoginRegistrationScreen(navController = navController!!)
                    }
                }
                // Define other destinations
            }
        }

        // Check initial state
        val firstPageTitle = composeTestRule.activity.getString(onboardingPages.first().titleResId)
        val firstPageDesc = composeTestRule.activity.getString(onboardingPages.first().descriptionResId)

        composeTestRule.onNodeWithText(firstPageTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(firstPageDesc).assertIsDisplayed()

        // Perform button click to navigate to the next page
        val nextButtonText = composeTestRule.activity.getString(R.string.next_button_text)
        composeTestRule.onNodeWithContentDescription(nextButtonText).performClick()

        // Check the updated state after navigating to the next page
        val secondPageTitle = composeTestRule.activity.getString(onboardingPages[1].titleResId)
        val secondPageDesc = composeTestRule.activity.getString(onboardingPages[1].descriptionResId)

        composeTestRule.onNodeWithText(secondPageTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(secondPageDesc).assertIsDisplayed()

        // Perform button click on the last page to trigger navigation to the next screen
        composeTestRule.onNodeWithContentDescription(nextButtonText).performClick()

        // Check the updated state after navigating to the next page
        val thirdPageTitle = composeTestRule.activity.getString(onboardingPages[2].titleResId)
        val thirdPageDesc = composeTestRule.activity.getString(onboardingPages[2].descriptionResId)

        composeTestRule.onNodeWithText(thirdPageTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(thirdPageDesc).assertIsDisplayed()

        // Perform button click on the last page to trigger navigation to the next screen
        composeTestRule.onNodeWithContentDescription(nextButtonText).performClick()

        // Check the updated state after navigating to the next page
        val forthPageTitle = composeTestRule.activity.getString(onboardingPages[3].titleResId)
        val forthPageDesc = composeTestRule.activity.getString(onboardingPages[3].descriptionResId)

        composeTestRule.onNodeWithText(forthPageTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(forthPageDesc).assertIsDisplayed()

        // Perform button click on the last page to trigger navigation to the next screen
        val getStartedButtonText = composeTestRule.activity.getString(R.string.get_started_button_text)
        composeTestRule.onNodeWithText(getStartedButtonText).performClick()

        // Assert navigation to the next screen (LoginRegistrationScreen)
        val currentDestination = composeTestRule.runOnIdle {
            navController?.currentBackStackEntry?.destination?.route
        }
        assertEquals(LoginRegistrationScreen.route, currentDestination)
    }

    @Test
    fun loginRegistrationScreen_to_bottomNavHost_navigationTest() = runBlocking {
        var navController: NavHostController? = null

        composeTestRule.setContent {
            navController = rememberNavController()

            NavHost(navController = navController!!, startDestination = LoginRegistrationScreen.route) {
                composable(route = LoginRegistrationScreen.route) {
                    CookItTheme {
                        LoginRegistrationScreen(navController = navController!!)
                    }
                }
                composable(route = BottomNavGraph.route) {
                    CookItTheme {
                        CookItBottomNavHost(navController = navController!!)
                    }
                }
                // Define other destinations
            }
        }

        // Find the email and password text fields
        val emailLabel = composeTestRule.activity.getString(R.string.email_text)
        val passwordLabel = composeTestRule.activity.getString(R.string.password_text)

        // Perform text input in the email field
        composeTestRule.onNode(hasText(emailLabel))
            .performTextInput("example@example.com")

        // Perform text input in the password field
        composeTestRule.onNode(hasText(passwordLabel))
            .performTextInput("password123")

        // Perform the navigation to the BottomNavGraph
        composeTestRule.runOnIdle {
            navController?.navigate(BottomNavGraph.route)
        }

        // Wait for the navigation to complete
        composeTestRule.waitForIdle()

        // Assert that the navigation to BottomNavGraph occurred
        val currentDestination = composeTestRule.runOnIdle {
            navController?.currentDestination?.route
        }
        assertEquals(BottomNavGraph.route, currentDestination)
    }

}