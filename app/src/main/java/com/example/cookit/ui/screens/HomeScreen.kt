package com.example.cookit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cookit.R
import com.example.cookit.models.mockCuisineTypes
import com.example.cookit.models.recipeList
import com.example.cookit.ui.common.RecipeCard
import com.example.cookit.ui.common.RecipeList
import com.example.cookit.ui.common.RoundedCuisineItemList
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.getFoodSuggestion
import com.example.cookit.utils.getGreetingText
import com.example.cookit.utils.showMessage

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val name = "Lorem"
    val cuisines = mockCuisineTypes
    var currentIndex by rememberSaveable { mutableStateOf(0) }
    var currentItem by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            .background(color = MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(5f)
            ) {
                Text(
                    text = getGreetingText(name),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colors.primaryVariant,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = stringResource(R.string.recipe_suggestion_text, getFoodSuggestion()),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://pbs.twimg.com/media/Fj31UzPWYAAXzzO?format=jpg&name=large")
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.user_profile_picture_description),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier
                    .weight(1f)
                    .size(50.dp)
                    .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true)
                    .clip(shape = CircleShape),
                alignment = Alignment.TopEnd
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        RecipeCard(
            modifier = Modifier.fillMaxWidth().height(180.dp),
            recipe = recipeList.random(),
            onItemClicked = {
                showMessage(
                    context = context,
                    message = "Navigate to recipe ${it.name} details screen"
                )
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        RoundedCuisineItemList(
            items = cuisines.map { item ->
                item.value.replaceFirstChar { it.uppercase() }
            }.sorted(),
            selectedIndex = currentIndex,
            onItemSelected = { index, item ->
                currentIndex = index
                currentItem = item
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        RecipeList(
            recipes = recipeList,
            onItemClicked = {
                showMessage(
                    context = context,
                    message = "Navigate to recipe ${it.name} details screen"
                )
            }
        )

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    CookItTheme {
        HomeScreen()
    }
}