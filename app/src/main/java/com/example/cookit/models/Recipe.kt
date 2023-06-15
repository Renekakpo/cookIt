package com.example.cookit.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cookit.models.converters.AnalyzedInstructionsConverters
import com.example.cookit.models.converters.StringListConverters
import com.example.cookit.utils.RECIPE_TABLE_NAME
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = RECIPE_TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
@Serializable
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0,
    val id: Long = 0,
    val title: String? = null,
    val image: String? = null,
    val imageType: String? = null,
    val summary: String? = null,
    val servings: Int? = 0,
    val readyInMinutes: Int? = 0,
    val preparationMinutes: Int? = 0,
    val cookingMinutes: Int? = 0,
    val license: String? = null,
    val sourceName: String? = null,
    val sourceUrl: String? = null,
    val spoonacularSourceUrl: String? = null,
    val aggregateLikes: Int? = 0,
    val healthScore: Double? = 0.0,
    val spoonacularScore: Double? = 0.0,
    val pricePerServing: Double? = 0.0,
    @TypeConverters(AnalyzedInstructionsConverters::class)
    val analyzedInstructions: List<AnalyzedRecipeInstructionsApiRes>? = null,
    val cheap: Boolean? = false,
    val creditsText: String? = null,
    @TypeConverters(StringListConverters::class)
    val cuisines: List<String>? = null,
    val dairyFree: Boolean? = false,
    @TypeConverters(StringListConverters::class)
    val diets: List<String>? = null,
    val gaps: String? = null,
    val glutenFree: Boolean? = false,
    val instructions: String? = null,
    val ketogenic: Boolean? = false,
    val lowFodmap: Boolean? = false,
    @TypeConverters(StringListConverters::class)
    val occasions: List<String>? = null,
    val sustainable: Boolean? = false,
    val vegan: Boolean? = false,
    val vegetarian: Boolean? = false,
    val veryHealthy: Boolean? = false,
    val veryPopular: Boolean? = false,
    val whole30: Boolean? = false,
    val weightWatcherSmartPoints: Int? = 0,
    @TypeConverters(StringListConverters::class)
    val dishTypes: List<String>? = null,
    @TypeConverters(ExtendedIngredient::class)
    val extendedIngredients: List<ExtendedIngredient> = emptyList(),
    @TypeConverters(Nutrition::class)
    val nutrition: Nutrition? = null,
    var cooked: Boolean = false
)

@Serializable
data class ExtendedIngredient(
    val aisle: String? = null,
    val amount: Double? = 0.0,
    @SerializedName("consitency")
    val consistency: String? = null,
    val id: Int? = 0,
    val image: String? = null,
    val measures: Map<String, Measure>,
    val meta: List<String>?,
    val name: String? = null,
    val nameClean: String? = null,
    val original: String? = null,
    val originalName: String? = null,
    val unit: String? = null
)

@Serializable
data class Measure(
    val amount: Double? = 0.0,
    val unitLong: String? = null,
    val unitShort: String? = null
)

@Serializable
data class Nutrition(
    val nutrients: List<Nutrient>? = null,
    val properties: List<NutProperty>? = null,
    val flavonoids: List<NutFlavonoid>? = null,
    val ingredients: List<NutIngredient>? = null,
    val caloricBreakdown: CaloricBreakdown? = null,
    val weightPerServing: WeightPerServing? = null,
)

@Serializable
data class Nutrient(
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null,
    val percentOfDailyNeeds: Double? = null
)

@Serializable
data class NutProperty(
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null
)

@Serializable
data class NutFlavonoid(
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null
)

@Serializable
data class NutIngredient(
    val id: Long = 0,
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null
)

@Serializable
data class CaloricBreakdown(
    val percentProtein: Double? = null,
    val percentFat: Double? = null,
    val percentCarbs: Double? = null
)

@Serializable
data class WeightPerServing(
    val amount: Double? = null,
    val unit: String? = null
)


