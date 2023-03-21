package com.example.cookit.models

import kotlinx.serialization.Serializable

@Serializable
data class RandomRecipesAPIRes(val recipes: List<Recipe>)
