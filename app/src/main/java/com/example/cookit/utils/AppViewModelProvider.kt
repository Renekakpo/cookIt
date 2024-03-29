package com.example.cookit.utils

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cookit.ui.screens.auth.LoginRegistrationViewModel
import com.example.cookit.ui.screens.favorite.FavoriteRecipeViewModel
import com.example.cookit.ui.screens.home.HomeViewModel
import com.example.cookit.ui.screens.onboarding.OnboardingViewModel
import com.example.cookit.ui.screens.recipeItem.RecipeInfoViewModel
import com.example.cookit.ui.screens.search.SearchViewModel
import com.example.cookit.ui.screens.settings.SettingsViewModel
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

        // Initializer for LoginRegistrationViewModel
        initializer {
            val dataStoreRepos = cookItApplication().cookItDataStore
            LoginRegistrationViewModel(dataStore = dataStoreRepos)
        }

        // Initializer for HomeViewModel
        initializer {
            val dataStoreRepos = cookItApplication().cookItDataStore
            val networkRepository = cookItApplication().networkDataContainer.cookItNetworkRepository
            HomeViewModel(cookItNetworkRepository = networkRepository, dataStore = dataStoreRepos)
        }

        // Initializer for SearchViewModel
        initializer {
            val dataStoreRepos = cookItApplication().cookItDataStore
            val networkRepository = cookItApplication().networkDataContainer.cookItNetworkRepository
            SearchViewModel(dataStore = dataStoreRepos, networkRepository = networkRepository)
        }

        // Initializer for RecipeInfoViewModel
        initializer {
            val networkRepository = cookItApplication().networkDataContainer.cookItNetworkRepository
            val recipesRepository = cookItApplication().offlineDataContainer.recipesRepository
            val dataStoreRepos = cookItApplication().cookItDataStore
            RecipeInfoViewModel(
                networkRepository = networkRepository,
                localDataSource = recipesRepository,
                localDataStore = dataStoreRepos
            )
        }

        // Initializer for FavoriteRecipeViewModel
        initializer {
            val recipeRepository = cookItApplication().offlineDataContainer.recipesRepository
            FavoriteRecipeViewModel(recipeRepository = recipeRepository)
        }

        // Initializer for SettingsViewModel
        initializer {
            val recipeRepository = cookItApplication().offlineDataContainer.recipesRepository
            val dataStoreRepos = cookItApplication().cookItDataStore
            SettingsViewModel(recipeRepository = recipeRepository, localDataStore = dataStoreRepos)
        }
    }
}