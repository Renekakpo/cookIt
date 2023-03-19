package com.example.cookit.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import com.example.cookit.models.Recipe
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch


sealed interface SearchUiState {
    object Loading : SearchUiState
    object Error : SearchUiState
    data class Success(val recipes: List<Recipe>) : SearchUiState
}

data class FilterUiState(
    val cuisine: String = "",
    val meal: String = "",
    val diet: String = "",
    val intolerances: Set<String> = emptySet()
)

class SearchViewModel(
    private val dataStore: CookItDataStoreRepository,
    private val cookItNetworkRepository: CookItNetworkRepository
) : ViewModel() {
    private var _filterUiState = MutableStateFlow(FilterUiState())
    val filterUiState: StateFlow<FilterUiState> = _filterUiState

    init {
        _filterUiState = combine(
            dataStore.cuisineTypeFilter,
            dataStore.mealTypeFilter,
            dataStore.dietFilter,
            dataStore.intolerancesFilter
        ) { cuisine, meal, diet, intolerances ->
            FilterUiState(cuisine = cuisine, meal = meal, diet = diet, intolerances = intolerances)
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = FilterUiState()
        ) as MutableStateFlow<FilterUiState>
    }

    fun searchRecipe(name: String) {
        viewModelScope.launch {
            // TODO: Search for a recipe using the API
        }
    }

    fun saveAllFilter(filterUiState: FilterUiState) {
        viewModelScope.launch {
            dataStore.saveAllFilter(filterUiState = filterUiState)
        }
    }

    fun clearAllFilter(filterUiState: FilterUiState) {
        viewModelScope.launch {
            dataStore.clearAllFilter()
        }
    }
}