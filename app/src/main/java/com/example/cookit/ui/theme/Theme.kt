package com.example.cookit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Teal400, // Primary color
    primaryVariant = Teal700, // Primary color variant
    secondary = Teal400, // Secondary color
    background = Color.Black, // Background color
    surface = Teal400.copy(alpha = 0.5f), // Surface color

    onPrimary = Color.White, // Text color on primary background in dark mode
    onSecondary = Color.White, // Text color on secondary background in dark mode
    onBackground = Color.White, // Text color on background in dark mode
    onSurface = Teal400, // Text color on surface in dark mode
)

private val LightColorPalette = lightColors(
    primary = Teal400, // Main color used to display components across the app
    primaryVariant = Teal700, // Variant of the main color used for components such as topBar and statusBar
    background = Color.White, // Background color
    secondary = Teal70O700, // Color used by components such as floating button, checkbox, highlight items...
    surface = Teal400,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.White
)

@Composable
fun CookItTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}