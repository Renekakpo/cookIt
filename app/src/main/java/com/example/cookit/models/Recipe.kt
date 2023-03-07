package com.example.cookit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String?,
    val url: String?,
    val rating: Float?,
    val cookingTime: Int
)

val recipeList = listOf(
    Recipe(name = "Spaghetti Carbonara", url = "https://www.example.com/spaghetti_carbonara.jpg", rating = 4.5f, cookingTime = 30),
    Recipe(name = "Chicken Alfredo", url = "https://www.example.com/chicken_alfredo.jpg", rating = 4.2f, cookingTime = 45),
    Recipe(name = "Mushroom Risotto",url = "https://www.example.com/mushroom_risotto.jpg",rating = 4.8f, cookingTime = 15),
    Recipe(name = "Mushroom Risotto",url = "https://www.example.com/mushroom_risotto.jpg",rating = 4.8f, cookingTime = 15),
    Recipe(name = "Mushroom Risotto",url = "https://www.example.com/mushroom_risotto.jpg",rating = 4.8f, cookingTime = 15),
    Recipe(name = "Mushroom Risotto",url = "https://www.example.com/mushroom_risotto.jpg",rating = 4.8f, cookingTime = 15)
)

