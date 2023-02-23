package com.example.cookit.data.offline

import android.content.Context
import com.example.cookit.database.CookItDatabase

/**
 * App container for Dependency injection.
 */
interface CookItAppContainer {
    val recipesRepository: RecipesRepository
}

/**
 * [CookItAppContainer] implementation that provides instance of [OfflineRecipeRepository]
 */
class CookItOfflineDataContainer(context: Context) : CookItAppContainer {
    /**
     * Implementation for [RecipesRepository]
     */
    override val recipesRepository: RecipesRepository by lazy {
        OfflineRecipeRepository(CookItDatabase.getDatabase(context).getRecipeDao())
    }
}

