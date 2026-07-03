package com.example.cookit.di

import android.content.Context
import com.example.cookit.database.CookItDatabase
import com.example.cookit.database.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCookItDatabase(@ApplicationContext context: Context): CookItDatabase =
        CookItDatabase.getDatabase(context)

    @Provides
    fun provideRecipeDao(database: CookItDatabase): RecipeDao = database.getRecipeDao()
}
