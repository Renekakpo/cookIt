package com.example.cookit.ui.screens.home

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import com.example.cookit.models.Recipe
import com.example.cookit.utils.getFoodSuggestion
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Error : HomeUiState
    data class Success(val randomRecipes: List<Recipe>) : HomeUiState
}

class HomeViewModel(
    private val cookItNetworkRepository: CookItNetworkRepository,
    private val dataStore: CookItDataStoreRepository
) : ViewModel() {
    var homeFilterState = dataStore.cuisineTypeFilter
    private set

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getRandomRecipes(country = null)
    }

    fun getRandomRecipes(country: String?) {
        viewModelScope.launch {
            homeUiState = try {
                val foodSuggestion = getFoodSuggestion().let {
                    if (it.contains(" ")) it.substringAfterLast(" ") else it
                }
                val result = cookItNetworkRepository.getRandomRecipes(
                    limitLicense = true,
                    tags = listOf(foodSuggestion, country ?: ""),
                    number = 10
                )
                HomeUiState.Success(randomRecipes = result ?: emptyList())
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
}