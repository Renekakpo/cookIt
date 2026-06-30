package com.example.cookit.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_PREFERENCE_NAME = "cookIt_app_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideCookItDataStoreRepository(
        dataStore: DataStore<Preferences>
    ): CookItDataStoreRepository = CookItDataStoreRepository(dataStore = dataStore)
}
