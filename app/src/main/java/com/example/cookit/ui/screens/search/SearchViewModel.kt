package com.example.cookit.ui.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.R
import com.example.cookit.app.CookIt.Companion.appContext
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import com.example.cookit.models.Recipe
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class Error(val errorMessage: String) : SearchUiState
    object NoQuery : SearchUiState
    data class Success(val recipes: List<Recipe>) : SearchUiState
}

data class FilterUiState(
    val cuisine: String = "",
    val meal: String = "",
    val diet: String = "",
    val intolerances: List<String>? = null
)

class SearchViewModel(
    private val dataStore: CookItDataStoreRepository,
    private val networkRepository: CookItNetworkRepository
) : ViewModel() {

    var searchUiState: SearchUiState by mutableStateOf(SearchUiState.NoQuery)
        private set

    private val _filterUiState = MutableStateFlow(FilterUiState())
    var filterUiState: StateFlow<FilterUiState> = _filterUiState.asStateFlow()
        private set

    init {
        combine(
            dataStore.cuisineTypeFilter,
            dataStore.mealTypeFilter,
            dataStore.dietFilter,
            dataStore.intolerancesFilter
        ) { cuisine, meal, diet, intolerances ->
            FilterUiState(
                cuisine = cuisine,
                meal = meal,
                diet = diet,
                intolerances = intolerances.takeIf { it.isNotEmpty() }?.filter { it.isNotEmpty() })
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = FilterUiState()
        ).onEach {
            _filterUiState.value = it
            Log.d("Filter", "$it")
        }.launchIn(scope = viewModelScope)
    }

    fun searchRecipe(query: String, filter: FilterUiState) {
        viewModelScope.launch {
            searchUiState = try {
                val res = networkRepository.searchRecipes(
                    query = query,
                    cuisine = filter.cuisine.ifEmpty { null },
                    diet = filter.diet.ifEmpty { null },
                    type = filter.meal.ifEmpty { null },
                    intolerances = filter.intolerances?.joinToString { ", " }
                )
                if (res.results.isNullOrEmpty()) {
                    SearchUiState.Error(errorMessage = appContext.getString(R.string.empty_search_results_text))
                } else {
                    SearchUiState.Success(recipes = res.results)
                }
            } catch (e: Exception) {
                Log.d("searchRecipe", "${e.message}")
                SearchUiState.Error(errorMessage = "${e.message}")
            }
        }
    }

    fun updateFilterUiState(
        cuisine: String? = null,
        meal: String? = null,
        diet: String? = null,
        intolerances: List<String>? = null
    ) {
        _filterUiState.update { currentState ->
            FilterUiState(
                cuisine = cuisine ?: currentState.cuisine,
                meal = meal ?: currentState.meal,
                diet = diet ?: currentState.diet,
                intolerances = intolerances ?: currentState.intolerances
            )
        }
    }

    fun saveAllFilter(filterUiState: FilterUiState) = viewModelScope.launch {
        dataStore.saveAllFilter(filterUiState = filterUiState)
    }

    fun clearAllFilter() = viewModelScope.launch {
        dataStore.clearAllFilter()
    }
}