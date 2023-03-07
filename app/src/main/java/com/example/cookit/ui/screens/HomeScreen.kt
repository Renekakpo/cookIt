package com.example.cookit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.cookit.ui.common.RecipeList
import com.example.cookit.ui.common.RoundedCuisineItemList
import com.example.cookit.ui.common.SearchField
import com.example.cookit.ui.common.TextWithIcon
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.getFoodSuggestion
import com.example.cookit.utils.getGreetingText

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val name = "Lorem"
    var queryChanged by remember { mutableStateOf("") }
    val cuisines = mockCuisineTypes
    var currentIndex by rememberSaveable { mutableStateOf(0) }
    var currentItem by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
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
                    text = getFoodSuggestion(),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("")
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.user_profile_picture_description),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.onboarding_cooking),
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

        SearchField(
            query = queryChanged,
            onQueryChanged = { queryChanged = it },
            onFilterClicked = {
                Toast.makeText(context, "Open filter screen", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data("")
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.user_profile_picture_description),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.onboarding_cooking),
                    placeholder = painterResource(id = R.drawable.onboarding_cooking),
                    modifier = Modifier.fillMaxWidth()
                )

                TextWithIcon(
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .padding(5.dp),
                    text = "4,5",
                    isTimeIcon = false
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black.copy(alpha = 0.5f))
                        .align(alignment = Alignment.BottomCenter)
                ) {
                    Text(
                        text = "Dish name goes here",
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
                        text = "15 min.",
                        isTimeIcon = true
                    )
                }
            }
        }

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
                Toast.makeText(
                    context,
                    "Navigate to recipe ${it.name} details screen",
                    Toast.LENGTH_SHORT
                )
                    .show()
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