package com.example.cookit.data.network

import com.example.cookit.models.RecipeSearchResult
import com.example.cookit.models.network.SpecificRecipeInfo
import com.example.cookit.network.CookItApiService

interface CookItNetworkRepository {
    suspend fun searchRecipes(
        type: String,
        appId: String,
        appKey: String,
        input: String,
        ingredient: String?,
        diet: List<String>?,
        health: List<String>?,
        cuisineType: List<String>?,
        dishType: List<String>?,
        mealType: List<String>?,
    ): RecipeSearchResult

    suspend fun getSpecificRecipeInfo(
        id: String,
        type: String,
        appId: String,
        appKey: String
    ): SpecificRecipeInfo
}

class DefaultCookItNetworkRepository(private val cookItApiService: CookItApiService) :
    CookItNetworkRepository {
    override suspend fun searchRecipes(
        type: String,
        appId: String,
        appKey: String,
        input: String,
        ingredient: String?,
        diet: List<String>?,
        health: List<String>?,
        cuisineType: List<String>?,
        dishType: List<String>?,
        mealType: List<String>?
    ): RecipeSearchResult {
        return cookItApiService.searchRecipes(
            type,
            appId,
            appKey,
            input,
            ingredient,
            diet,
            health,
            cuisineType,
            dishType,
            mealType
        )
    }

    override suspend fun getSpecificRecipeInfo(
        id: String,
        type: String,
        appId: String,
        appKey: String
    ): SpecificRecipeInfo {
        return cookItApiService.getSpecificRecipeInfo(id, type, appId, appKey)
    }
}