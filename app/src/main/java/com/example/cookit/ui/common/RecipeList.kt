package com.example.cookit.ui.common


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(recipes) { index, recipe ->
            RecipeCard(recipe = recipe, index = index, onItemClicked = onItemClicked)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeCard(recipe: Recipe, index: Int = 0, onItemClicked: (Recipe) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        modifier = Modifier.width(180.dp),
        onClick = { onItemClicked(recipe) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipe.url) // TODO: Recipe image url
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.user_profile_picture_description),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.onboarding_cooking),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier.fillMaxWidth()
            )

            TextWithIcon(
                modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(5.dp),
                text = "${recipe.rating}", // TODO: Recipe rating value
                isTimeIcon = false
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(alpha = 0.5f))
                    .align(alignment = Alignment.BottomCenter)
            ) {
                Text(
                    text = "${recipe.name}", // TODO: Recipe name
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 5.dp)
                )

                TextWithIcon(
                    modifier = Modifier.padding(5.dp),
                    text = "${recipe.cookingTime} min.", // TODO: Recipe cooking time
                    isTimeIcon = true
                )
            }
        }
    }
}