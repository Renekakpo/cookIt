package com.example.cookit.ui.common


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cookit.R
import com.example.cookit.models.Recipe

@Composable
fun RecipeList(recipes: List<Recipe>, onItemClicked: (Recipe) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        itemsIndexed(recipes) { _, recipe ->
            RecipeCard(
                modifier = Modifier.width(180.dp),
                recipe = recipe,
                onItemClicked = onItemClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeCard(modifier: Modifier, recipe: Recipe, onItemClicked: (Recipe) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        modifier = modifier.background(color = Color.Transparent),
        onClick = { onItemClicked(recipe) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipe.image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.recipe_image_description),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(alpha = 0.5f))
                    .align(alignment = Alignment.BottomCenter)
            ) {
                Text(
                    text = "${recipe.title}",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                )

                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextWithLeftIcon(
                        text = "${recipe.readyInMinutes} min.",
                        isTimeIcon = true
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TextWithLeftIcon(
                        text = "${recipe.spoonacularScore}",
                        isTimeIcon = false
                    )
                }
            }
        }
    }
}