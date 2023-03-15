package com.example.cookit.utils

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cookit.ui.screens.onboarding.OnboardingViewModel
import com.example.cookit.ui.screens.splash.SplashViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for SplashViewModel
        initializer {
            SplashViewModel(dataStore = cookItApplication().cookItDataStore)
        }

        // Initializer for OnboardingViewModel
        initializer {
            OnboardingViewModel(dataStore = cookItApplication().cookItDataStore)
        }
    }
}