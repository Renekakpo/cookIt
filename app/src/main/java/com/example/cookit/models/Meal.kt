package com.example.cookit.models

data class MealType(
    val value: String
)

val breakfast = MealType("breakfast")
val brunch = MealType("brunch")
val lunchOrDinner = MealType("lunch/dinner")
val snack = MealType("snack")
val teatime = MealType("teatime")

val mockMealTypes = listOf(breakfast, brunch, lunchOrDinner, snack, teatime)