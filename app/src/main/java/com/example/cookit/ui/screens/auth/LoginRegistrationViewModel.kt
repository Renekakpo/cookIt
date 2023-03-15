package com.example.cookit.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import kotlinx.coroutines.launch

class LoginRegistrationViewModel(private val dataStore: CookItDataStoreRepository): ViewModel() {
    fun saveLoginCompletionState(isCompleted: Boolean) {
        viewModelScope.launch {
            dataStore.saveLoginCompletionState(
                isLoginCompleted = isCompleted)
        }
    }
}