package com.example.cookit.models

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedRecipeInstructionsApiRes(
    val name: String,
    val steps: List<Step>
)

@Serializable
data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>
)

@Serializable
data class Ingredient(
    val id: Long,
    val name: String,
    val localizedName: String,
    val image: String,
)

@Serializable
data class Equipment(
    val id: Long,
    val name: String,
    val localizedName: String,
    val image: String,
)
