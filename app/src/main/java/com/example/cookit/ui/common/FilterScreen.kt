package com.example.cookit.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookit.R
import com.example.cookit.ui.screens.search.SearchViewModel
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider

@Composable
fun FilterScreen(searchViewModel: SearchViewModel, closeBottomSheet: () -> Unit) {
    val filterUiState by searchViewModel.filterUiState.collectAsState()
    val cuisines = stringArrayResource(id = R.array.cuisine)
        .map { item -> item.replaceFirstChar { it.uppercase() } }
        .sorted()
    val meals = stringArrayResource(id = R.array.meal)
        .map { item -> item.replaceFirstChar { it.uppercase() } }
        .sorted()
    val diets = stringArrayResource(id = R.array.diet)
        .map { item -> item.replaceFirstChar { it.uppercase() } }
        .sorted()
    val intolerances = stringArrayResource(id = R.array.intolerances)
        .map { item -> item.replaceFirstChar { it.uppercase() } }
        .sorted()

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
            cuisineTypes = cuisines,
            selectedCuisineType = filterUiState.cuisine,
            onCuisineTypeSelected = { cuisine ->
                searchViewModel.updateFilterUiState(cuisine = cuisine)
            }
        )

        MealTypeFilter(
            mealTypes = meals,
            selectedMealType = filterUiState.meal,
            onMealTypeSelected = { meal ->
                searchViewModel.updateFilterUiState(meal = meal)
            }
        )

        DietFilter(
            diets = diets,
            selectedDiet = filterUiState.diet,
            onDietSelected = { diet ->
                searchViewModel.updateFilterUiState(diet = diet)
            }
        )

        IntoleranceFilter(
            intolerances = intolerances,
            selectedIntolerances = filterUiState.intolerances.map { it.lowercase() }.toSet(),
            onIntoleranceSelected = { intolerances ->
                searchViewModel.updateFilterUiState(intolerances = intolerances)
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            TextButton(
                onClick = {
                    searchViewModel.clearAllFilter()
                    closeBottomSheet()
                },
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
                onClick = {
                    searchViewModel.saveAllFilter(filterUiState = filterUiState)
                    closeBottomSheet()
                },
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
        FilterScreen(
            searchViewModel = viewModel(factory = AppViewModelProvider.Factory),
            closeBottomSheet = {}
        )
    }
}