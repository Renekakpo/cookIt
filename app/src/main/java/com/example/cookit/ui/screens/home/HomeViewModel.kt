package com.example.cookit.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import com.example.cookit.models.RandomRecipesAPIRes
import com.example.cookit.models.Recipe
import com.example.cookit.utils.getFoodSuggestion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Error : HomeUiState
    data class Success(val randomRecipes: RandomRecipesAPIRes) : HomeUiState
}

class HomeViewModel(
    private val cookItNetworkRepository: CookItNetworkRepository,
    private val dataStore: CookItDataStoreRepository
) : ViewModel() {
    var homeFilterState = dataStore.cuisineTypeFilter
    private set

    private val _randomRecipe = MutableStateFlow<Recipe?>(value = null)
    val randomRecipe: StateFlow<Recipe?>
        get() = _randomRecipe.asStateFlow()


    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

//    init {
//        getRandomRecipes(country = null)
//    }

    fun getRandomRecipes(country: String?) {
        homeUiState = HomeUiState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            homeUiState = try {
                val foodSuggestion = getFoodSuggestion().let {
                    if (it.contains(" ")) it.substringAfterLast(" ") else it
                }
                val result = cookItNetworkRepository.getRandomRecipes(
                    limitLicense = true,
                    tags = listOf(foodSuggestion, country ?: ""),
                    number = 10
                )
                updateRandomRecipeValue(newValue = result.recipes.random())
                HomeUiState.Success(randomRecipes = result)
            } catch (e: Exception) {
                Log.d("getRandomRecipes", "${e.message}")
                HomeUiState.Error
            }
        }
    }

    fun saveCuisine(value: String) {
        viewModelScope.launch {
            dataStore.saveCuisineTypeFilter(newValue = value)
        }
    }

    private fun updateRandomRecipeValue(newValue: Recipe?) {
        _randomRecipe.value = newValue
    }
}