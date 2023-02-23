package com.example.cookit.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookit.models.network.Digest
import com.example.cookit.models.network.Images
import com.example.cookit.models.network.Ingredient
import com.example.cookit.models.network.Nutrient

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val uri: String,
    val label: String,
    val image: String,
    val images: Images,
    val source: String,
    val url: String,
    val shareAs: String,
    val yield: Int,
    val dietLabels: List<String>,
    val healthLabels: List<String>,
    val cautions: List<String>,
    val ingredientLines: List<String>,
    val ingredients: List<Ingredient>,
    val calories: Double,
    val glycemicIndex: Double,
    val totalCO2Emissions: Double,
    val co2EmissionsClass: String,
    val totalWeight: Double,
    val cuisineType: List<String>,
    val mealType: List<String>,
    val dishType: List<String>,
    val instructions: List<String>,
    val tags: List<String>,
    val externalId: String,
    val totalNutrients: Map<String, Nutrient>,
    val totalDaily: Map<String, Nutrient>,
    val digest: List<Digest>,
    val recipeId: String = uri.substringAfterLast("recipe_")
)
