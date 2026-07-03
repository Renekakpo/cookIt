package com.example.cookit.ui.screens.recipeItem

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.data.Resource
import com.example.cookit.data.offline.RecipesRepository
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import com.example.cookit.models.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface RecipeDetailsUiState {
    object Loading : RecipeDetailsUiState
    data class Success(
        val recipe: Recipe,
        val isFavorite: Boolean,
        val fromCache: Boolean,
    ) : RecipeDetailsUiState
    object ErrorOfflineNoCache : RecipeDetailsUiState
    data class Error(val message: String?) : RecipeDetailsUiState
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RecipeInfoViewModel @Inject constructor(
    private val localDataSource: RecipesRepository,
    private val localDataStore: CookItDataStoreRepository,
) : ViewModel() {

    private val recipeId = MutableStateFlow<Long?>(null)

    val uiState = recipeId
        .filterNotNull()
        .flatMapLatest { id ->
            // Single source of the Recipe: recipeDetail (cache-first, then network).
            // getItemStream is used ONLY to derive isFavorite as a Boolean - never to carry data -
            // so the row's presence keeps the heart live without a second network call.
            val isFavoriteFlow = localDataSource.getItemStream(id)
                .map { it != null }
                .distinctUntilChanged()

            combine(
                localDataSource.recipeDetail(id),
                isFavoriteFlow,
            ) { resource, isFavorite ->
                when (resource) {
                    Resource.Loading -> RecipeDetailsUiState.Loading
                    is Resource.Success -> RecipeDetailsUiState.Success(
                        recipe = resource.data,
                        isFavorite = isFavorite,
                        fromCache = resource.fromCache,
                    )
                    Resource.Error.OfflineNoCache -> RecipeDetailsUiState.ErrorOfflineNoCache
                    is Resource.Error.Network -> RecipeDetailsUiState.Error(resource.message)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecipeDetailsUiState.Loading,
        )

    fun load(id: Long) {
        recipeId.value = id
    }

    fun updateFavoriteDataState(isFavorite: Boolean) {
        // Insert exactly the Recipe currently displayed. For a non-favorite this is the network
        // object served by recipeDetail() (never written to Room) - so favoriting caches that object,
        // not an empty one and without a re-fetch.
        val recipe = (uiState.value as? RecipeDetailsUiState.Success)?.recipe ?: return
        favoriteOp("updateFavoriteDataState") {
            if (isFavorite) {
                localDataSource.insertItem(recipe = recipe)
            } else {
                localDataSource.deleteItem(id = recipe.id)
            }
        }
    }

    fun incrementCookedCount() = favoriteOp("incrementCookedCount") {
        localDataStore.updateCookedCount()
    }

    private fun favoriteOp(tag: String, block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                Log.d(tag, "${e.message}")
            }
        }
    }
}
