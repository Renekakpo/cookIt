package com.example.cookit.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.ui.common.CuisineTypeFilter
import com.example.cookit.ui.common.DietFilter
import com.example.cookit.ui.common.IntoleranceFilter
import com.example.cookit.ui.common.MealTypeFilter
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun FilterScreen(
    onClearFilter: (Map<String, List<String>>) -> Unit,
    onApplyFilter: (Map<String, List<String>>) -> Unit
) {
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

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Filter Recipes",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )

        CuisineTypeFilter(
            cuisineTypes = stringArrayResource(id = R.array.cuisine).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedCuisineType = cuisineType,
            onCuisineTypeSelected = { cuisineType = it }
        )

        MealTypeFilter(
            mealTypes = stringArrayResource(id = R.array.meal).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedMealType = mealType,
            onMealTypeSelected = { mealType = it }
        )

        DietFilter(
            diets = stringArrayResource(id = R.array.diet).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedDiet = diet,
            onDietSelected = { diet = "$it" }
        )

        IntoleranceFilter(
            intolerances = stringArrayResource(id = R.array.intolerances).map { item -> item.replaceFirstChar { it.uppercase() } }
                .sorted(),
            selectedIntolerances = intolerancesList.toSet(),
            onIntoleranceSelected = { intolerancesList = it.toList() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            OutlinedButton(
                onClick = { onClearFilter(filters) },
                modifier = Modifier
                    .padding(16.dp)
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.primary)
            ) {
                Text(
                    text = "Clear All",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onApplyFilter(filters) },
                modifier = Modifier
                    .padding(16.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Apply Filter",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun FilterScreenPopup(
    isOpen: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isOpen,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ) + fadeIn(
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ) + fadeOut(
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black.copy(alpha = 0.5f)
                )
                .clickable(
                    onClick = onClose,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 500.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                    .padding(16.dp)
                    .then(modifier),
                contentAlignment = Alignment.TopCenter
            ) {
                FilterScreen(onClearFilter = { onClose() }, onApplyFilter = {})
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