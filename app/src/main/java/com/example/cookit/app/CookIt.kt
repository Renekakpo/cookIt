package com.example.cookit.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.cookit.data.network.CookItNetworkContainer
import com.example.cookit.data.network.DefaultCookItNetworkContainer
import com.example.cookit.data.offline.CookItAppContainer
import com.example.cookit.data.offline.CookItOfflineDataContainer
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository

private const val APP_PREFERENCE_NAME = "cookIt_app_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCE_NAME)

class CookIt : Application() {

    /**
     * [CookItAppContainer] instance used by the rest of classes to obtain dependencies
     */
    lateinit var offlineDataContainer: CookItAppContainer

    /**
     * [CookItNetworkContainer] instance used by the rest of classes to obtain dependencies
     */
    lateinit var networkDataContainer: CookItNetworkContainer
    lateinit var cookItDataStore: CookItDataStoreRepository
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        offlineDataContainer = CookItOfflineDataContainer(this)
        networkDataContainer = DefaultCookItNetworkContainer()
        cookItDataStore = CookItDataStoreRepository(dataStore = dataStore)
    }
}