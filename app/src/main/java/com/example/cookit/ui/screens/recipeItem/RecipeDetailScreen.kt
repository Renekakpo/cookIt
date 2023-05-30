package com.example.cookit.ui.screens.recipeItem

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val cuisines = stringArrayResource(id = R.array.cuisine).sorted()
    val items = cuisines.map { item -> item.replaceFirstChar { it.uppercase() } }.sorted()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(color = MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .size(25.dp)
                    .clickable { onBackClicked() }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Reciept",
                style = MaterialTheme.typography.h2,
                modifier = Modifier.wrapContentSize(Alignment.Center),
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

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
                .height(200.dp)
                .padding(start = 15.dp, end = 15.dp),
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "${recipe.title}",
            style = MaterialTheme.typography.h4,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
                .border(
                    border = BorderStroke(width = 2.dp, color = Color.Black.copy(alpha = 0.4f)),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Column (
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "350",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Kcal",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column (
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "250",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "grams",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column (
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "4.7",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "rating",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column (
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "15",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "minutes",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items) {item ->
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary.copy(alpha = 0.1f),
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primary
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier= Modifier.padding(start = 15.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "6 Items",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp, end = 15.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(recipe.extendedIngredients) { extendedIngredient ->
                IngredientItem(ingredient = extendedIngredient)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp, )
        ) {
            IconButton(
                modifier = Modifier.background(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.05f),
                    shape = MaterialTheme.shapes.medium
                ),
                onClick = onLikeClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Like recipe",
                    tint = Color.Black.copy(alpha = 0.4f)
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = onStartCookingClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = "Start Cooking",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Like recipe",
                    tint = MaterialTheme.colors.onPrimary
                )
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