package com.example.cookit.ui.screens.recipeItem

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.cookit.R
import com.example.cookit.models.ExtendedIngredient
import com.example.cookit.models.Recipe
import com.example.cookit.models.Step
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.common.ErrorScreen
import com.example.cookit.ui.common.LoadingScreen
import com.example.cookit.utils.AppViewModelProvider
import com.example.cookit.utils.INGREDIENT_IMAGE_BASE_URL
import com.example.cookit.utils.showMessage
import kotlinx.coroutines.launch

object RecipeDetailScreen : NavDestination {
    override val route: String = "recipe_info"
    var itemID: Long? = null
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeDetailsScreenMainContainer(
    id: Long,
    navigateUp: () -> Unit,
    viewModel: RecipeInfoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val stepsState =
        remember { mutableStateOf<List<Step>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetElevation = 8.dp,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(
            topStart = 25.dp,
            topEnd = 25.dp
        ),
        sheetContent = {
            AnalyzedInstructionsSheet(
                steps = stepsState.value
            )
        }
    ) {
        RecipeDetailsScreen(
            id = id,
            navigateUp = navigateUp,
            onStartCookingClicked = { steps ->
                stepsState.value = steps
                coroutineScope.launch { modalSheetState.show() }
            },
            viewModel = viewModel
        )
    }
}

@Composable
fun RecipeDetailsScreen(
    id: Long,
    navigateUp: () -> Unit,
    onStartCookingClicked: (List<Step>) -> Unit,
    viewModel: RecipeInfoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current

    val localRecipeData: Recipe? by viewModel.localRecipeData.collectAsState()
    val addedToFavoriteRecipes: Boolean by viewModel.addedToFavorite.collectAsState()

    when (val recipeUiState = viewModel.recipeDetailsUiState) {
        is RecipeDetailsUiState.Loading -> {
            LoadingScreen()
        }

        is RecipeDetailsUiState.Success -> {
            val recipe = recipeUiState.recipe

            RecipeDetailsScreenContent(
                onLikeClicked = { state ->
                    viewModel.updateFavoriteDataState(
                        isFavorite = state,
                        recipe = recipe
                    )
                },
                onStartCookingClicked = onStartCookingClicked,
                navigateUp = navigateUp,
                recipe = recipe,
                addedToFavoriteRecipes = addedToFavoriteRecipes
            )

            if (addedToFavoriteRecipes) {
                viewModel.updateFavoriteRecipeDetails(item = recipe)
            }
        }

        is RecipeDetailsUiState.Updated -> {
            showMessage(
                context = context,
                message = "Recipe data updated"
            )
        }

        is RecipeDetailsUiState.Error -> {
            if (localRecipeData != null) {
                RecipeDetailsScreenContent(
                    onLikeClicked = { state ->
                        viewModel.updateFavoriteDataState(
                            isFavorite = state,
                            recipe = localRecipeData!!
                        )
                    },
                    onStartCookingClicked = onStartCookingClicked,
                    navigateUp = navigateUp,
                    recipe = localRecipeData!!,
                    addedToFavoriteRecipes = true
                )
            } else {
                ErrorScreen(
                    errorMessage = stringResource(R.string.data_retrieving_error_message),
                    onRetry = { viewModel.getNetworkRecipeDetails(recipeId = id) }
                )
            }
        }
    }

    LaunchedEffect(id) {
        viewModel.isFavoriteRecipe(recipeId = id)
        viewModel.getNetworkRecipeDetails(recipeId = id)
        viewModel.getLocalRecipeDetails(recipeId = id)
    }
}

@Composable
fun RecipeDetailsScreenContent(
    onLikeClicked: (Boolean) -> Unit,
    onStartCookingClicked: (List<Step>) -> Unit,
    navigateUp: () -> Unit,
    recipe: Recipe,
    addedToFavoriteRecipes: Boolean = false
) {
    val context = LocalContext.current

    var isFavorite by remember { mutableStateOf(addedToFavoriteRecipes) }

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
                    .clickable { navigateUp() }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.recipe_details_screen_title),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.wrapContentSize(Alignment.Center),
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 4.dp,
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(start = 15.dp, end = 15.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(alpha = 0.3f))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(recipe.image)
                        .crossfade(enable = true)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = "Recipe image",
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "${recipe.title?.replaceFirstChar { it.uppercase() }}",
            style = MaterialTheme.typography.h6,
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
                    border = BorderStroke(width = 2.dp, color = Color.Black.copy(alpha = 0.1f)),
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${recipe.nutrition?.caloricBreakdown?.percentFat?.toInt() ?: 0}%",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Fat",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${recipe.nutrition?.caloricBreakdown?.percentProtein?.toInt() ?: 0}%",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Proteins",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${recipe.servings ?: 0}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Servings",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black.copy(alpha = 0.4f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${recipe.readyInMinutes ?: 0}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primaryVariant
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Minutes",
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
            items(recipe.dishTypes ?: emptyList()) { item ->
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary.copy(alpha = 0.1f),
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    Text(
                        text = item.replaceFirstChar { it.uppercase() },
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
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ingredients_text),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.items_text, "${recipe.extendedIngredients.size}"),
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
            contentPadding = PaddingValues(vertical = 10.dp),
        ) {
            items(recipe.extendedIngredients) { extendedIngredient ->
                IngredientItem(ingredient = extendedIngredient)
            }
        }

        Spacer(
            modifier = Modifier
                .height(5.dp)
                .background(color = Color.Transparent)
        )

        Row(
            modifier = Modifier
                .padding(start = 15.dp, bottom = 10.dp, end = 15.dp)
                .background(color = Color.Transparent)
        ) {
            IconButton(
                modifier = Modifier.background(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.05f),
                    shape = MaterialTheme.shapes.medium
                ),
                onClick = {
                    isFavorite = !isFavorite
                    onLikeClicked(isFavorite)
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_icon_description),
                    tint = if (isFavorite) Color.Red.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.4f)
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = {
                    if (recipe.analyzedInstructions.isNullOrEmpty()) {
                        showMessage(context, "No instructions found")
                    } else {
                        onStartCookingClicked(recipe.analyzedInstructions.first().steps)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(R.string.start_cooking_button_text),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "",
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
            .padding(start = 5.dp, end = 5.dp, bottom = 8.dp)
            .clickable { /* Do nothing */ }
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.05f),
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$INGREDIENT_IMAGE_BASE_URL${ingredient.image}")
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.ingredient_image_description),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(7.dp)
                    .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = "${ingredient.name?.replaceFirstChar { it.uppercase() }}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = "${ingredient.amount} ${ingredient.unit}",
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun AnalyzedInstructionsSheet(
    steps: List<Step>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 15.dp, bottom = 15.dp, end = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(5.dp)
                .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
                .align(alignment = Alignment.CenterHorizontally)
        ) {}

        Text(
            text = stringResource(R.string.instructions_text),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 15.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            itemsIndexed(steps) { _, step ->
                InstructionsItem(
                    modifier = Modifier.heightIn(min = 48.dp),
                    step = step
                )
            }
        }
    }
}

@Composable
fun InstructionsItem(modifier: Modifier, step: Step) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${step.number}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Text(
            text = step.step,
            color = Color.DarkGray,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .align(alignment = Alignment.CenterVertically)
        )
    }
}