package com.example.cookit.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookit.R
import com.example.cookit.navigation.CookItMainNavHost
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.screens.FavoriteScreen
import com.example.cookit.ui.screens.HomeScreen
import com.example.cookit.ui.screens.SearchScreen
import com.example.cookit.ui.screens.SettingsScreen
import com.example.cookit.ui.theme.CookItTheme

object CookItScreen : NavDestination {
    override val route: String = "cookIt_screen"
}

@Composable
fun CookItApp(modifier: Modifier = Modifier, navHostController: NavHostController) {
    CookItMainNavHost(navController = navHostController, modifier = modifier)
}


@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CookItAppPreview() {
    CookItTheme {
        CookItApp(navHostController = rememberNavController())
    }
}