package com.example.cookit.ui.screens.recipeItem

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.data.offline.RecipesRepository
import com.example.cookit.models.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed interface RecipeInfoUiState {
    object Loading : RecipeInfoUiState
    object Error : RecipeInfoUiState
    object Updated : RecipeInfoUiState
    data class Success(val recipe: Recipe) : RecipeInfoUiState
}

class RecipeInfoViewModel(
    private val networkRepository: CookItNetworkRepository,
    private val localDataSource: RecipesRepository
) : ViewModel() {

    var recipeInfoUiState: RecipeInfoUiState by mutableStateOf(RecipeInfoUiState.Loading)
        private set

    fun getRecipeInfo(recipeId: Long) {
        recipeInfoUiState = RecipeInfoUiState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            recipeInfoUiState = try {
                val result = networkRepository.getRecipeInfo(id = recipeId)
                RecipeInfoUiState.Success(recipe = result)
            } catch (e: Exception) {
                Log.d("getRecipeInfo", "${e.message}")
                RecipeInfoUiState.Error
            }
        }
    }

    fun addRecipeToFavorite(newItem: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localDataSource.insertItem(recipe = newItem)
            } catch (e: Exception) {
                Log.d("Add to favorite", "${e.message}")
            }
        }
    }

    fun updateFavoriteRecipeDetails(itemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeInfoUiState = try {
                val result = networkRepository.getRecipeInfo(id = itemId)
                localDataSource.updateItem(recipe = result)
                RecipeInfoUiState.Updated
            } catch (e: Exception) {
                Log.d("updateFavoriteRecipeDetails", "${e.message}")
                RecipeInfoUiState.Error
            }
        }
    }
}