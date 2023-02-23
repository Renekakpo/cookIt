package com.example.cookit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cookit.models.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
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
                    name = "cookIt_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}