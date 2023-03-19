package com.example.cookit.models

data class AnalyzedRecipeInstructionsApiRes(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>
)

data class Ingredient(
    val id: String,
    val name: String,
    val localizedName: String,
    val image: String,
)

data class Equipment(
    val id: String,
    val name: String,
    val localizedName: String,
    val image: String,
)
