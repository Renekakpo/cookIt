package com.example.cookit.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.offline.RecipesRepository
import com.example.cookit.models.Recipe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class FavoriteRecipeUiState(val favorites: List<Recipe> = listOf())

/**
 * View model to retrieve all recipes from Room database
 */
class FavoriteRecipeViewModel(recipeRepository: RecipesRepository) : ViewModel() {

    val favoriteRecipeUiState: StateFlow<FavoriteRecipeUiState> =
        recipeRepository.getAllItemsStream().map {
            FavoriteRecipeUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLI),
            initialValue = FavoriteRecipeUiState()
        )

    companion object {
        private const val TIMEOUT_MILLI = 5_000L
    }
}