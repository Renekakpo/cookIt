package com.example.cookit.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cookit.R

@Composable
fun DietFilter(
    diets: List<String>,
    selectedDiet: String,
    onDietSelected: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.diet_text),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground,
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(diets) { diet ->
                val isSelected = diet.lowercase() == selectedDiet.lowercase()
                DietChip(
                    diet = diet,
                    isSelected = isSelected,
                    onClick = { onDietSelected(diet) }
                )
            }
        }
    }
}

@Composable
fun DietChip(
    diet: String,
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
            text = diet,
            color = contentColor,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}