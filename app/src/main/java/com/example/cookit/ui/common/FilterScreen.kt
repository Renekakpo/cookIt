package com.example.cookit.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookit.R
import com.example.cookit.ui.screens.search.SearchViewModel
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider
import com.example.cookit.utils.showMessage

@Composable
fun FilterScreen(searchViewModel: SearchViewModel) {
    val context = LocalContext.current
    val filterUiState = searchViewModel.filterUiState.collectAsState().value
    var cuisineType by rememberSaveable { mutableStateOf(filterUiState.cuisine) }
    var mealType by rememberSaveable { mutableStateOf(filterUiState.meal) }
    var diet by rememberSaveable { mutableStateOf(filterUiState.diet) }
    var intolerancesList by rememberSaveable { mutableStateOf(filterUiState.intolerances.toList()) }

    Column(
        modifier = Modifier.background(color = Color.White)
    ) {
        Text(
            text = stringResource(R.string.recipe_filter_modal_title),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )

        CuisineTypeFilter(
            cuisineTypes = stringArrayResource(id = R.array.cuisine).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedCuisineType = cuisineType,
            onCuisineTypeSelected = {
                cuisineType = it
                showMessage(context = context, "Selected meal type: $it")
            }
        )

        MealTypeFilter(
            mealTypes = stringArrayResource(id = R.array.meal).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedMealType = mealType,
            onMealTypeSelected = {
                mealType = it
                showMessage(context = context, "Selected meal type: $it")
            }
        )

        DietFilter(
            diets = stringArrayResource(id = R.array.diet).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedDiet = diet,
            onDietSelected = {
                diet = it
                showMessage(context = context, "Selected diet: $it")
            }
        )

        IntoleranceFilter(
            intolerances = stringArrayResource(id = R.array.intolerances).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedIntolerances = intolerancesList.toSet(),
            onIntoleranceSelected = {
                intolerancesList = it.toList()
                showMessage(context = context, "Selected intolerances: $it")
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            TextButton(
                onClick = { searchViewModel.clearAllFilter(filterUiState = filterUiState) },
                modifier = Modifier.padding(16.dp),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.primary)
            ) {
                Text(
                    text = stringResource(R.string.clear_all_filter_text),
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { searchViewModel.saveAllFilter(filterUiState = filterUiState) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.apply_filter_button_text),
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    CookItTheme {
        FilterScreen(searchViewModel = viewModel(factory = AppViewModelProvider.Factory))
    }
}