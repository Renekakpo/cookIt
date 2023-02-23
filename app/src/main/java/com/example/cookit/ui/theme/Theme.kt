package com.example.cookit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Teal700, // Primary color
    primaryVariant = Teal700, // Primary color variant
    secondary = Teal400, // Secondary color
    background = Color.Black, // Background color
    surface = TealA200, // Surface color
    onPrimary = Color.White, // Text color on primary background in dark mode
    onSecondary = Color.White, // Text color on secondary background in dark mode
    onBackground = Color.White, // Text color on background in dark mode
    onSurface = Color.White, // Text color on surface in dark mode
)

private val LightColorPalette = lightColors(
    primary = Teal700, // Primary color
    primaryVariant = Teal400, // Primary color variant
    secondary = Teal400, // Secondary color
    background = Color.White, // Background color
    surface = Teal700, // Surface color
    onPrimary = Color.White, // Text color on primary background in light mode
    onSecondary = Color.White, // Text color on secondary background in light mode
    onBackground = Color.Black, // Text color on background in light mode
    onSurface = Color.White, // Text color on surface in light mode
)

@Composable
fun CookItTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}