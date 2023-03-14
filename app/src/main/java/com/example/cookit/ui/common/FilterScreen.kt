package com.example.cookit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.ui.common.CuisineTypeFilter
import com.example.cookit.ui.common.DietFilter
import com.example.cookit.ui.common.IntoleranceFilter
import com.example.cookit.ui.common.MealTypeFilter
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.showMessage

@Composable
fun FilterScreen(
    onClearFilter: (Map<String, List<String?>>) -> Unit,
    onApplyFilter: (Map<String, List<String?>>) -> Unit
) {
    val context = LocalContext.current
    var cuisineType by rememberSaveable { mutableStateOf("") }
    var mealType by rememberSaveable { mutableStateOf("") }
    var diet by rememberSaveable { mutableStateOf("") }
    var intolerancesList by rememberSaveable { mutableStateOf(listOf<String>()) }

    val filters = mapOf(
        "cuisineType" to listOf(cuisineType),
        "mealType" to listOf(mealType),
        "diet" to listOf(diet),
        "intolerances" to intolerancesList
    )

    Column(
        modifier = Modifier.background(color = Color.White)
    ) {
        Text(
            text = "Filter Recipes",
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
                showMessage(context = context, "Selected meal type: $it")}
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
                onClick = { onClearFilter(filters) },
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
                onClick = { onApplyFilter(filters) },
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
        FilterScreen(onClearFilter = {}, onApplyFilter = {})
    }
}