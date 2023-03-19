package com.example.cookit.utils

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cookit.ui.screens.home.HomeViewModel
import com.example.cookit.ui.screens.onboarding.OnboardingViewModel
import com.example.cookit.ui.screens.recipeItem.RecipeInfoViewModel
import com.example.cookit.ui.screens.splash.SplashViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for SplashViewModel
        initializer {
            val dataStoreRepos = cookItApplication().cookItDataStore
            SplashViewModel(dataStore = dataStoreRepos)
        }

        // Initializer for OnboardingViewModel
        initializer {
            val dataStoreRepos = cookItApplication().cookItDataStore
            OnboardingViewModel(dataStore = dataStoreRepos)
        }

        // Initializer for HomeViewModel
        initializer {
            val dataStoreRepos = cookItApplication().cookItDataStore
            val networkRepository = cookItApplication().networkDataContainer.cookItNetworkRepository
            HomeViewModel(cookItNetworkRepository = networkRepository, dataStore = dataStoreRepos)
        }

        // Initializer for RecipeInfoViewModel
        initializer {
            val dataStoreRepos = cookItApplication().cookItDataStore
            val networkRepository = cookItApplication().networkDataContainer.cookItNetworkRepository
            RecipeInfoViewModel(networkRepository = networkRepository)
        }
    }
}