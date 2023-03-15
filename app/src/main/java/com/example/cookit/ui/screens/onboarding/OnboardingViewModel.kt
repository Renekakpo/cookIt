package com.example.cookit.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(private val dataStore: CookItDataStoreRepository) : ViewModel() {
    fun saveOnboardingCompletionState(isCompleted: Boolean) {
        viewModelScope.launch {
            dataStore.saveOnBoardingCompletionState(
                isOnBoardingCompleted = isCompleted
            )
        }
    }
}