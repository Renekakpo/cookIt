package com.example.cookit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.models.recipeList
import com.example.cookit.ui.common.SearchField
import com.example.cookit.ui.common.VerticalGridList
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.showMessage

@Composable
fun SearchScreen() {
    val context = LocalContext.current
    var queryChanged by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent)
        .padding(top = 15.dp, start = 15.dp, end = 15.dp)) {

        SearchField(
            query = queryChanged,
            onQueryChanged = { queryChanged = it },
            onFilterClicked = {
                showMessage(context = context, message = "Open filter screen")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        VerticalGridList(items = recipeList, onItemClicked = { showMessage(context, "Recipe clicked: ${it.name}") })

    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    CookItTheme {
        SearchScreen()
    }
}