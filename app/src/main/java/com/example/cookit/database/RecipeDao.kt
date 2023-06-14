package com.example.cookit.database

import androidx.room.*
import com.example.cookit.models.Recipe
import com.example.cookit.utils.RECIPE_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleRecipes(recipes: List<Recipe>)

    @Update
    suspend fun update(recipe: Recipe)

    @Query("DELETE FROM $RECIPE_TABLE_NAME WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM $RECIPE_TABLE_NAME WHERE id = :id")
    fun getItem(id: Long): Recipe?

    @Query("SELECT * FROM $RECIPE_TABLE_NAME ORDER BY title ASC")
    fun getAllItems(): Flow<List<Recipe>>
}