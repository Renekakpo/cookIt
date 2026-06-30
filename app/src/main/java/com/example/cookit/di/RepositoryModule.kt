package com.example.cookit.di

import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.data.network.DefaultCookItNetworkRepository
import com.example.cookit.data.offline.OfflineRecipeRepository
import com.example.cookit.data.offline.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecipesRepository(
        impl: OfflineRecipeRepository
    ): RecipesRepository

    @Binds
    @Singleton
    abstract fun bindCookItNetworkRepository(
        impl: DefaultCookItNetworkRepository
    ): CookItNetworkRepository
}
