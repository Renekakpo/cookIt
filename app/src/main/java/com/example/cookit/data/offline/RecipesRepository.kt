package com.example.cookit.data.offline

import com.example.cookit.database.RecipeDao
import com.example.cookit.models.Recipe
import kotlinx.coroutines.flow.Flow


/**
 * Repository that provides insert, update, delete, and retrieve of [Recipe] from a given data source.
 */
interface RecipesRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Recipe>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun  getItemStream(id: Long): Recipe?

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(recipe: Recipe)

    /**
     * Insert item in the data source
     */
    suspend fun insertItems(recipes: List<Recipe>)

    /**
     * Delete item from the data source base on its id
     */
    suspend fun deleteItem(id: Long)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(recipe: Recipe)
}

class OfflineRecipeRepository(private val recipeDao: RecipeDao): RecipesRepository {
    override fun getAllItemsStream(): Flow<List<Recipe>> = recipeDao.getAllItems()

    override fun getItemStream(id: Long): Recipe? = recipeDao.getItem(id)

    override suspend fun insertItem(recipe: Recipe) = recipeDao.insert(recipe)

    override suspend fun insertItems(recipes: List<Recipe>) = recipeDao.insertMultipleRecipes(recipes)

    override suspend fun deleteItem(id: Long) = recipeDao.delete(id)

    override suspend fun updateItem(recipe: Recipe) = recipeDao.update(recipe)
}