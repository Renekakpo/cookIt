package com.example.cookit.models

import com.example.cookit.models.Recipe

data class RecipeSearchResult(
    val offset: Int,
    val number: Int,
    val result: List<Recipe>
)