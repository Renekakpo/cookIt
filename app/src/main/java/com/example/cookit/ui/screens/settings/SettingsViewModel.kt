package com.example.cookit.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.offline.RecipesRepository
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(private val recipeRepository: RecipesRepository, private val localDataStore: CookItDataStoreRepository): ViewModel() {

    fun getFavoriteRecipesCount(): Flow<Int> {
        return recipeRepository.getAllItemsStream()
            .map { items -> items.size }
    }

    fun getCookedRecipesCount(): Flow<Long> {
        return localDataStore.cookedRecipesCount
    }
}