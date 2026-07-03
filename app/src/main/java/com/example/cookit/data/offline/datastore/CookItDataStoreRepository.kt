package com.example.cookit.data.offline.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.cookit.ui.screens.search.FilterUiState
import com.example.cookit.ui.theme.ThemeMode
import com.example.cookit.utils.appJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.IOException

class CookItDataStoreRepository(private val dataStore: DataStore<Preferences>) {
    val cuisineTypeFilter: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs -> prefs[CUISINE_TYPE_FILTER] ?: "" }

    suspend fun saveCuisineTypeFilter(newValue: String) {
        dataStore.edit { it[CUISINE_TYPE_FILTER] = newValue}
    }

    val mealTypeFilter: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs -> prefs[MEAL_TYPE_FILTER] ?: "" }


    val dietFilter: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs -> prefs[DIET_FILTER] ?: "" }

    val intolerancesFilter: Flow<List<String>> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs ->
            val listAsString = prefs[INTOLERANCES_FILTER] ?: ""
            if (listAsString.isEmpty()) {
                emptyList()
            } else {
                try {
                    appJson.decodeFromString<List<String>>(listAsString)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

    suspend fun saveAllFilter(filterUiState: FilterUiState) {
        dataStore.edit {
            it[CUISINE_TYPE_FILTER] = filterUiState.cuisine
            it[MEAL_TYPE_FILTER] = filterUiState.meal
            it[DIET_FILTER] = filterUiState.diet
            it[INTOLERANCES_FILTER] = appJson.encodeToString(filterUiState.intolerances.orEmpty())
        }
    }

    suspend fun clearAllFilter() {
        dataStore.edit {
            it.apply {
                remove(CUISINE_TYPE_FILTER)
                remove(MEAL_TYPE_FILTER)
                remove(DIET_FILTER)
                remove(INTOLERANCES_FILTER)
            }
        }
    }

    val cookedRecipesCount: Flow<Long> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs -> prefs[RECIPE_COOKED_COUNT] ?: 0 }

    suspend fun updateCookedCount() {
        dataStore.edit {
            val prevCount = it[RECIPE_COOKED_COUNT] ?: 0
            it[RECIPE_COOKED_COUNT] =  prevCount.inc()
        }
    }

    val themeMode: Flow<ThemeMode> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs ->
            val name = prefs[THEME_MODE]
            runCatching { ThemeMode.valueOf(name ?: "") }.getOrDefault(ThemeMode.SYSTEM)
        }

    suspend fun saveThemeMode(mode: ThemeMode) {
        dataStore.edit { it[THEME_MODE] = mode.name }
    }

    private companion object {
        val TAG: String = CookItDataStoreRepository::class.java.simpleName
        val CUISINE_TYPE_FILTER = stringPreferencesKey(name = "cuisine_type_filter")
        val MEAL_TYPE_FILTER = stringPreferencesKey(name = "meal_type_filter")
        val DIET_FILTER = stringPreferencesKey(name = "diet_filter")
        val INTOLERANCES_FILTER = stringPreferencesKey(name = "intolerances_filter")
        val RECIPE_COOKED_COUNT = longPreferencesKey(name = "recipe_cooked_count")
        val THEME_MODE = stringPreferencesKey(name = "theme_mode")
    }
}