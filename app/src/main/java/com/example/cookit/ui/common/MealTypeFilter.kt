package com.example.cookit.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MealTypeFilter(
    mealTypes: List<String>,
    selectedMealType: String?,
    onMealTypeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Meal Type",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground,
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mealTypes.size) { index ->
                val mealType = mealTypes[index]
                val isSelected = mealType == selectedMealType
                MealTypeChip(
                    mealType = mealType,
                    isSelected = isSelected,
                    onMealTypeSelected = onMealTypeSelected
                )
            }
        }
    }
}

@Composable
fun MealTypeChip(
    mealType: String,
    isSelected: Boolean,
    onMealTypeSelected: (String) -> Unit
) {
    val colors = MaterialTheme.colors
    val chipBackgroundColor =
        if (isSelected) colors.primary else colors.primary.copy(alpha = 0.3f)
    val textColor =
        if (isSelected) colors.onPrimary else Color.DarkGray.copy(alpha = 0.87f)

    Surface(
        modifier = Modifier.clickable { onMealTypeSelected(mealType) },
        elevation = 0.dp,
        shape = MaterialTheme.shapes.small,
        color = chipBackgroundColor
    ) {
        Text(
            text = mealType,
            color = textColor,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}