package com.example.cookit.models

data class DietLabel(
    val type: String,
//    @SerializedName("web_label")
    val webLabel: String,
//    @SerializedName("api_parameter")
    val apiParameter: String,
    val definition: String
)

val balancedDiet =
    DietLabel("Diet", "Balanced", "balanced", "Protein/Fat/Carb values in 15/35/50 ratio")
val highFiberDiet = DietLabel("Diet", "High-Fiber", "high-fiber", "More than 5g fiber per serving")
val highProteinDiet = DietLabel(
    "Diet",
    "High-Protein",
    "high-protein",
    "More than 50% of total calories from proteins"
)
val lowCarbDiet =
    DietLabel("Diet", "Low-Carb", "low-carb", "Less than 20% of total calories from carbs")
val lowFatDiet = DietLabel("Diet", "Low-Fat", "low-fat", "Less than 15% of total calories from fat")
val lowSodiumDiet = DietLabel("Diet", "Low-Sodium", "low-sodium", "Less than 140mg Na per serving")

val mockDietLabels =
    listOf(balancedDiet, highFiberDiet, highProteinDiet, lowCarbDiet, lowFatDiet, lowSodiumDiet)
//val dietJson = Gson().toJson(dietList)
