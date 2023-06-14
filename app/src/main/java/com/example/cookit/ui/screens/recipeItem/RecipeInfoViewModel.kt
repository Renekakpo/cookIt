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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed interface RecipeDetailsUiState {
    object Loading : RecipeDetailsUiState
    object Error : RecipeDetailsUiState
    object Updated : RecipeDetailsUiState
    data class Success(val recipe: Recipe) : RecipeDetailsUiState
}

class RecipeInfoViewModel(
    private val networkRepository: CookItNetworkRepository,
    private val localDataSource: RecipesRepository
) : ViewModel() {

    var recipeDetailsUiState: RecipeDetailsUiState by mutableStateOf(RecipeDetailsUiState.Loading)
        private set

    val localRecipeData: MutableStateFlow<Recipe?> = MutableStateFlow(value = null)
    val addedToFavorite: MutableStateFlow<Boolean> = MutableStateFlow(value = false)

    fun getNetworkRecipeDetails(recipeId: Long) {
        recipeDetailsUiState = RecipeDetailsUiState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            recipeDetailsUiState = try {
                val result = networkRepository.getRecipeInfo(id = recipeId)
                RecipeDetailsUiState.Success(recipe = result)
            } catch (e: Exception) {
                Log.d("getRecipeInfo", "${e.message}")
                RecipeDetailsUiState.Error
            }
        }
    }

    private fun addRecipeToFavorite(newItem: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localDataSource.insertItem(recipe = newItem)
            } catch (e: Exception) {
                Log.d("Add to favorite", "${e.message}")
            }
        }
    }

    fun isFavoriteRecipe(recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            addedToFavorite.value = try {
                val recipe = localDataSource.getItemStream(id = recipeId)
                recipe != null
            } catch (e: Exception) {
                Log.d("isFavoriteRecipe", "${e.message}")
                false
            }
        }
    }

    fun getLocalRecipeDetails(recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            localRecipeData.value = try {
                localDataSource.getItemStream(id = recipeId)
            } catch (e: Exception) {
                Log.d("isFavoriteRecipe", "${e.message}")
                null
            }
        }
    }

    fun updateFavoriteRecipeDetails(item: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localDataSource.updateItem(recipe = item)
            } catch (e: Exception) {
                Log.d("updateFavoriteRecipeDetails", "${e.message}")
            }
        }
    }

    private fun removeFromFavorite(itemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localDataSource.deleteItem(id = itemId)
            } catch (e: Exception) {
                Log.d("removeFromFavorite", "${e.message}")
            }
        }
    }

    fun updateFavoriteDataState(isFavorite: Boolean, recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isFavorite) { // Add to favorite recipes
                    addRecipeToFavorite(newItem = recipe)
                } else { // Remove from favorite recipes
                    removeFromFavorite(itemId = recipe.id)
                }

            } catch (e: Exception) {
                Log.d("updateFavoriteDataState", "${e.message}")
            }
        }
    }
}