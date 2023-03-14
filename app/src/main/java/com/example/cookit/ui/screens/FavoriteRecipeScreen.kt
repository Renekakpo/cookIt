package com.example.cookit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.models.recipeList
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.common.VerticalGridList
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.showMessage

object FavoriteRecipeScreen: NavDestination {
    override val route: String = "favorite_recipes"
}

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(modifier.fillMaxSize().padding(15.dp)) {
        VerticalGridList(
            items = recipeList,
            onItemClicked = { showMessage(context, "Recipe clicked: ${it.title}") })
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    CookItTheme {
        FavoriteScreen()
    }
}