package com.example.cookit.data.offline.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.cookit.ui.screens.search.FilterUiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class CookItDataStoreRepository(private val dataStore: DataStore<Preferences>) {
    val onBoardingCompleted: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs -> prefs[ON_BOARDING_COMPLETED] ?: true }

    suspend fun saveOnBoardingCompletionState(isOnBoardingCompleted: Boolean) {
        dataStore.edit { prefs -> prefs[ON_BOARDING_COMPLETED] = isOnBoardingCompleted }
    }

    val loginCompletionState: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.d(TAG, "Error reading preferences", it)
                emit(value = emptyPreferences())
            } else {
                throw it
            }
        }.map { prefs -> prefs[ON_LOGIN_COMPLETED] ?: true }

    suspend fun saveLoginCompletionState(isLoginCompleted: Boolean) {
        dataStore.edit { prefs -> prefs[ON_LOGIN_COMPLETED] = isLoginCompleted }
    }

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
            val listType = object : TypeToken<List<String>>() {}.type
            val intolerances = Gson().fromJson<List<String>>(listAsString, listType)
            intolerances ?: emptyList()
        }

    suspend fun saveAllFilter(filterUiState: FilterUiState) {
        dataStore.edit {
            it[CUISINE_TYPE_FILTER] = filterUiState.cuisine
            it[MEAL_TYPE_FILTER] = filterUiState.meal
            it[DIET_FILTER] = filterUiState.diet
            it[INTOLERANCES_FILTER] = filterUiState.intolerances.toString()
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

    private companion object {
        val TAG: String = CookItDataStoreRepository::class.java.simpleName
        val ON_BOARDING_COMPLETED = booleanPreferencesKey(name = "onBoarding_completed")
        val ON_LOGIN_COMPLETED = booleanPreferencesKey(name = "on_login_completed")
        val CUISINE_TYPE_FILTER = stringPreferencesKey(name = "cuisine_type_filter")
        val MEAL_TYPE_FILTER = stringPreferencesKey(name = "meal_type_filter")
        val DIET_FILTER = stringPreferencesKey(name = "diet_filter")
        val INTOLERANCES_FILTER = stringPreferencesKey(name = "intolerances_filter")
    }
}