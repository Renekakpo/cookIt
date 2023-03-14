package com.example.cookit.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CuisineTypeFilter(
    cuisineTypes: List<String>,
    selectedCuisineType: String,
    onCuisineTypeSelected: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)) {
        Text(
            text = "Cuisine Type",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cuisineTypes) { cuisineType ->
                val isSelected = cuisineType == selectedCuisineType
                CuisineTypeChip(
                    cuisineType = cuisineType,
                    isSelected = isSelected,
                    onClick = { onCuisineTypeSelected(cuisineType) }
                )
            }
        }
    }
}

@Composable
fun CuisineTypeChip(
    cuisineType: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colors
    val backgroundColor = if (isSelected) colors.primary else colors.primary.copy(alpha = 0.3f)
    val contentColor = if (isSelected) colors.onPrimary else Color.DarkGray.copy(alpha = 0.87f)

    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        elevation = 0.dp,
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Text(
            text = cuisineType,
            color = contentColor,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}