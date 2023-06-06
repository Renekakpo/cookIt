package com.example.cookit.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.cookit.navigation.CookItMainNavHost
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun CookItApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    CookItMainNavHost(modifier = modifier, navHostController = navController)
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CookItAppPreview() {
    CookItTheme {
        CookItApp()
    }
}