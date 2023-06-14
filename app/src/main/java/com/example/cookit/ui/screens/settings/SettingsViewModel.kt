package com.example.cookit.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.offline.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(private val recipeRepository: RecipesRepository): ViewModel() {

    fun getCountOfItems(): Flow<Int> {
        return recipeRepository.getAllItemsStream()
            .map { items -> items.size }
    }
}