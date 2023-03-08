package com.example.cookit.ui.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.cookit.models.Recipe

@Composable
fun VerticalGridList(items: List<Recipe>, onItemClicked: (Recipe) -> Unit) {
    val columns = 2
    // Calculate item width based on screen width and column count
    val itemWidth = (LocalConfiguration.current.screenWidthDp.dp - 16.dp * (columns + 1)) / columns

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 135.dp),
        modifier = Modifier.fillMaxSize().background(color = Color.Transparent),
        contentPadding = PaddingValues(5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.chunked(columns).forEach { rowItems ->
            itemsIndexed(rowItems) { _, item ->
                RecipeCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    recipe = item,
                    onItemClicked = { onItemClicked(item) }
                )
            }
        }
    }
}