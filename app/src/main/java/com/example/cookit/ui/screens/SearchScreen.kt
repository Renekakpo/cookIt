package com.example.cookit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.models.recipeList
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.common.SearchField
import com.example.cookit.ui.common.VerticalGridList
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.showMessage
import kotlinx.coroutines.launch


object SearchScreen: NavDestination {
    override val route: String = "search_screen"
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetContent = {
            FilterScreen(
                onClearFilter = { coroutineScope.launch { modalSheetState.hide() } },
                onApplyFilter = { coroutineScope.launch { modalSheetState.hide() } }
            )
        },
    ) {
        // Content of the main screen
        var queryChanged by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            ) {

                SearchField(
                    query = queryChanged,
                    onQueryChanged = { queryChanged = it },
                    onFilterClicked = {
                        coroutineScope.launch { modalSheetState.show() }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                VerticalGridList(
                    items = recipeList,
                    onItemClicked = { showMessage(context, "Recipe clicked: ${it.title}") })
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    CookItTheme {
        SearchScreen()
    }
}