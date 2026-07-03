package com.example.cookit.data.offline

import com.example.cookit.data.Resource
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.database.RecipeDao
import com.example.cookit.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


/**
 * Repository that provides insert, update, delete, and retrieve of [Recipe] from a given data source.
 */
interface RecipesRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Recipe>>

    /**
     * Retrieve an item from the given data source that matches with the [id] as a reactive stream.
     */
    fun getItemStream(id: Long): Flow<Recipe?>

    /**
     * NetworkBoundResource-style detail load, restricted to favorites (row present in Room = favorite).
     * Emits cache first when present, then refreshes from network (preserving `cooked`). Non-favorites
     * are never written to Room. Offline (IOException) falls back to cache or [Resource.Error.OfflineNoCache].
     */
    fun recipeDetail(id: Long): Flow<Resource<Recipe>>

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

class OfflineRecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val networkRepository: CookItNetworkRepository,
) : RecipesRepository {
    override fun getAllItemsStream(): Flow<List<Recipe>> = recipeDao.getAllItems()

    override fun getItemStream(id: Long): Flow<Recipe?> = recipeDao.getItemStream(id)

    override fun recipeDetail(id: Long): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading)

        // Presence in the table = favorite (status quo). Drives the favorite vs non-favorite branch.
        val cache = recipeDao.getItemOnce(id)
        if (cache != null) {
            emit(Resource.Success(data = cache, fromCache = true))
        }

        try {
            val network = networkRepository.getRecipeInfo(id = id)
            if (cache != null) {
                // Favorite: refresh the cached row but keep its primary key and `cooked` flag.
                val merged = network.copy(roomId = cache.roomId, cooked = cache.cooked)
                recipeDao.update(merged) // Room re-emits to getItemStream observers
                emit(Resource.Success(data = merged, fromCache = false))
            } else {
                // Non-favorite: serve the network result without caching it.
                emit(Resource.Success(data = network, fromCache = false))
            }
        } catch (io: IOException) {
            // Offline. Keep the cache Success already emitted; only error when there is no cache.
            if (cache == null) {
                emit(Resource.Error.OfflineNoCache)
            }
        } catch (e: Exception) {
            // HttpException and friends. Same rule: don't clobber a shown cache.
            if (cache == null) {
                emit(Resource.Error.Network(e.message))
            }
        }
    }

    override suspend fun insertItem(recipe: Recipe) = recipeDao.insert(recipe)

    override suspend fun insertItems(recipes: List<Recipe>) = recipeDao.insertMultipleRecipes(recipes)

    override suspend fun deleteItem(id: Long) = recipeDao.delete(id)

    override suspend fun updateItem(recipe: Recipe) = recipeDao.update(recipe)
}
