package com.example.cookit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cookit.R
import com.example.cookit.models.ExtendedIngredient
import com.example.cookit.models.Recipe
import com.example.cookit.models.recipe
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.theme.CookItTheme


object RecipeDetailScreen: NavDestination {
    override val route: String = "recipe_info"
}

@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    onBackClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onStartCookingClicked: () -> Unit,
    navigateUp: () -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(state = rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    ),
                    onClick = onBackClicked
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    ),
                    onClick = onLikeClicked
                ) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = "Like recipe",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipe.image)
                    .crossfade(enable = true)
                    .build(),
                contentDescription = "Recipe image",
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${recipe.title}",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${recipe.summary}",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(recipe.extendedIngredients) { extendedIngredient ->
                    IngredientItem(ingredient = extendedIngredient)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartCookingClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Start Cooking")
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: ExtendedIngredient) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface.copy(alpha = 0.8f))
            .clickable { /* Do nothing */ }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ingredient.image)
                .crossfade(true)
                .build(),
            contentDescription = "Ingredient image",
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = "${ingredient.name}",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${ingredient.measures["Us"]?.amount}${ingredient.measures["Us"]?.unitShort}",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            )
        }
    }
}

@Preview
@Composable
fun RecipeDetailsScreenPreview() {
    CookItTheme {
        RecipeDetailsScreen(
            recipe = recipe,
            onBackClicked = {},
            onLikeClicked = {},
            onStartCookingClicked = {},
            navigateUp = {},
        )
    }
}