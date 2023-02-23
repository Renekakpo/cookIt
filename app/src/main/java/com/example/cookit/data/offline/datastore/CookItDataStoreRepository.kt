package com.example.cookit.data.offline.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class CookItDataStoreRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val TAG: String = CookItDataStoreRepository::class.java.simpleName
    }
}