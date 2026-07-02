package com.example.cookit.models

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedRecipeInstructionsApiRes(
    val name: String = "",
    val steps: List<Step> = emptyList()
)

@Serializable
data class Step(
    val number: Int = 0,
    val step: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val equipment: List<Equipment> = emptyList()
)

@Serializable
data class Ingredient(
    val id: Long = 0,
    val name: String = "",
    val localizedName: String = "",
    val image: String = "",
)

@Serializable
data class Equipment(
    val id: Long = 0,
    val name: String = "",
    val localizedName: String = "",
    val image: String = "",
)
