package com.example.cookit.ui.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookit.R
import com.example.cookit.ui.common.VerticalGridList
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onRecipeSelected: (Long) -> Unit,
    favoriteRecipeViewModel: FavoriteRecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val favoriteRecipeList by favoriteRecipeViewModel.favoriteRecipeUiState.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Text(
            text = stringResource(R.string.favorite_recipe_title_text),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(R.string.favorite_recipe_subtitle_text),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (favoriteRecipeList.favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.favorite_recipe_empty_content_text),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                    textAlign = TextAlign.Justify
                )
            }
        } else {
            VerticalGridList(
                items = favoriteRecipeList.favorites,
                onItemClicked = { recipe -> onRecipeSelected(recipe.id) }
            )
        }
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    CookItTheme {
        FavoriteScreen(onRecipeSelected = {})
    }
}