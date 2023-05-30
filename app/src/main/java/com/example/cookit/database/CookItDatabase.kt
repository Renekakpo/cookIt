package com.example.cookit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cookit.models.Recipe
import com.example.cookit.models.converters.AnalyzedInstructionsConverters
import com.example.cookit.models.converters.ExtendedIngredientConverters
import com.example.cookit.models.converters.StringListConverters

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(
    AnalyzedInstructionsConverters::class,
    ExtendedIngredientConverters::class,
    StringListConverters::class
)
abstract class CookItDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: CookItDatabase? = null

        fun getDatabase(context: Context): CookItDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = CookItDatabase::class.java,
                    name = "app_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}