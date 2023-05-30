package com.example.cookit.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookit.R
import com.example.cookit.ui.common.*
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    modalSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var filter by remember { mutableStateOf(FilterUiState()) }

    LaunchedEffect(searchViewModel.filterUiState) {
        val filterUiState = searchViewModel.filterUiState.first()
        filter = filterUiState
    }

    // Content of the main screen
    SearchScreenMainContainer(
        modalBottomSheetState = modalSheetState,
        coroutineScope = coroutineScope,
        searchViewModel = searchViewModel,
        onQuerySubmit = { input ->
            searchViewModel.searchRecipe(
                query = input,
                filter = filter
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreenMainContainer(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    onQuerySubmit: (String) -> Unit,
    searchViewModel: SearchViewModel
) {
    var queryChanged by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.search_screen_title),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(id = R.string.search_screen_subtitle),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SearchField(
            query = queryChanged,
            onQueryChanged = { queryChanged = it },
            onQuerySubmit = onQuerySubmit,
            onFilterClicked = {
                coroutineScope.launch { modalBottomSheetState.show() }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        when (val searchUiState = searchViewModel.searchUiState) {
            is SearchUiState.NoQuery -> {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                        .padding(all = 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.search_screen_content_description),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
            is SearchUiState.Loading -> {
                LoadingScreen(modifier = modifier)
            }
            is SearchUiState.Error -> {
                ErrorScreen(
                    modifier = modifier,
                    errorMessage = searchUiState.errorMessage,
                    onRetry = { searchViewModel.searchRecipe(query = "", filter = FilterUiState()) }
                )
            }
            is SearchUiState.Success -> {
                VerticalGridList(
                    items = searchUiState.recipes,
                    onItemClicked = {
                        // TODO: Display recipe info
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SearchScreenPreview() {
    CookItTheme {
        SearchScreen(
            modalSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
                skipHalfExpanded = true,
            ),
            coroutineScope = rememberCoroutineScope(),
        )
    }
}