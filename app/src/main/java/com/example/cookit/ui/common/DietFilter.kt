package com.example.cookit.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DietFilter(
    diets: List<String>,
    selectedDiet: String,
    onDietSelected: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val icon = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowRight

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp)) {
        Text(
            text = "Diet",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedDiet.ifEmpty { "Diet" },
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = icon,
                contentDescription = "Expand/Collapse",
                tint = MaterialTheme.colors.primary
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            diets.forEach { diet ->
                DropdownMenuItem(
                    onClick = {
                        onDietSelected("${diet.takeIf { it != selectedDiet }}")
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = diet == selectedDiet,
                            onClick = {
                                onDietSelected("${diet.takeIf { it != selectedDiet }}")
                                expanded = false
                            }
                        )
                        Text(
                            text = diet,
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}