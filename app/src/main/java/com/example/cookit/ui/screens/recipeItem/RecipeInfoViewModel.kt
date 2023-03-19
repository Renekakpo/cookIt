package com.example.cookit.ui.screens.recipeItem

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.models.Recipe
import kotlinx.coroutines.launch

sealed interface RecipeInfoUiState {
    object Loading : RecipeInfoUiState
    object Error : RecipeInfoUiState
    data class Success(val recipe: Recipe) : RecipeInfoUiState
}

class RecipeInfoViewModel(private val networkRepository: CookItNetworkRepository) :
    ViewModel() {
    var recipeInfoUiState: RecipeInfoUiState by mutableStateOf(RecipeInfoUiState.Loading)
        private set

    fun getRecipeInfo(recipeId: Long) {
        viewModelScope.launch {
            recipeInfoUiState = try {
                val result = networkRepository.getRecipeInfo(id = recipeId)
                RecipeInfoUiState.Success(recipe = result)
            } catch (e: Exception) {
                Log.d("getRecipeInfo", "${e.message}")
                RecipeInfoUiState.Error
            }
        }
    }
}