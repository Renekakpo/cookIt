package com.example.cookit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import com.example.cookit.ui.theme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(repository: CookItDataStoreRepository) : ViewModel() {
    val themeMode: StateFlow<ThemeMode> = repository.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)
}
