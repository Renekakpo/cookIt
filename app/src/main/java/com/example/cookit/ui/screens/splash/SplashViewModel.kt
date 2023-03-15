package com.example.cookit.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import kotlinx.coroutines.flow.*

data class SplashUiState(
    val isOnboardingCompleted: Boolean = false,
    val isLoginCompleted: Boolean = false
)

/**
 * View model of SplashScreen component
 */
class SplashViewModel(dataStore: CookItDataStoreRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    var uiState: StateFlow<SplashUiState> = _uiState

    init {
        combine(
            dataStore.onBoardingCompleted,
            dataStore.loginCompletionState
        ) { onBoardingCompleted, loginCompleted ->
            SplashUiState(
                isOnboardingCompleted = onBoardingCompleted,
                isLoginCompleted = loginCompleted
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SplashUiState()
        ).onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }
}