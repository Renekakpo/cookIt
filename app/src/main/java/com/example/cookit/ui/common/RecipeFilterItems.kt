package com.example.cookit.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RecipeFilterItems(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(items) { _, item ->
            val backgroundColor = if (item.lowercase() == selectedItem?.lowercase())
                MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
            else
                MaterialTheme.colors.onBackground.copy(alpha = 0.1f)

            val textColor = if (item.lowercase() == selectedItem?.lowercase())
                MaterialTheme.colors.primary
            else
                MaterialTheme.colors.onBackground

            Box(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable {
                        onItemSelected(item)
                    }
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 15.dp),
                    fontWeight = if (item.lowercase() == selectedItem?.lowercase())
                        FontWeight.Bold
                    else
                        FontWeight.Normal,
                    style = MaterialTheme.typography.body1,
                    color = textColor
                )
            }
        }
    }

   /* LaunchedEffect(selectedItem) {
        val selectedIndex =
            selectedItem?.let { it -> items.indexOf(it.replaceFirstChar { it.uppercase() }) } ?: 0
        if (selectedIndex >= 0) {
            listState.animateScrollToItem(selectedIndex)
        }
    }*/
}