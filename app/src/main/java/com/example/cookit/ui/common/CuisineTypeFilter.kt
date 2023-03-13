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
import androidx.compose.ui.unit.dp

@Composable
fun CuisineTypeFilter(
    cuisineTypes: List<String>,
    selectedCuisineType: String,
    onCuisineTypeSelected: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = "Cuisine Type",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(end = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cuisineTypes) { cuisineType ->
                val isSelected = cuisineType == selectedCuisineType
                CuisineTypeChip(
                    cuisineType = cuisineType,
                    isSelected = isSelected,
                    onClick = { onCuisineTypeSelected(cuisineType) }
                )
                Spacer(Modifier.width(8.dp))
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
    val backgroundColor = if (isSelected) colors.primary else colors.onSurface.copy(alpha = 0.2f)
    val contentColor = if (isSelected) colors.onPrimary else colors.onSurface

    Surface(
        modifier = Modifier.clickable(onClick = onClick)
            .padding(end = 8.dp),
        elevation = 0.dp,
        shape = MaterialTheme.shapes.small,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Text(
            text = cuisineType,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}