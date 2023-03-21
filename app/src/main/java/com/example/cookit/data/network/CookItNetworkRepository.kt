package com.example.cookit.data.network

import com.example.cookit.models.RandomRecipesAPIRes
import com.example.cookit.models.Recipe
import com.example.cookit.models.SearchApiRes
import com.example.cookit.network.CookItApiService
import com.example.cookit.utils.APP_API_KEY

interface CookItNetworkRepository {
    suspend fun searchRecipes(
        apiKey: String = APP_API_KEY,
        query: String,
        cuisine: String?,
        diet: String?,
        type: String?,
        intolerances: String?,
    ): SearchApiRes

    suspend fun getRecipeInfo(
        apiKey: String = APP_API_KEY,
        id: Long,
        includeNutrition: Boolean = false,
    ): Recipe

    suspend fun getRandomRecipes(
        apiKey: String = APP_API_KEY,
        limitLicense: Boolean?,
        tags: List<String>?,
        number: Int
    ): RandomRecipesAPIRes
}

class DefaultCookItNetworkRepository(private val cookItApiService: CookItApiService) :
    CookItNetworkRepository {
    override suspend fun searchRecipes(
        apiKey: String,
        query: String,
        cuisine: String?,
        diet: String?,
        type: String?,
        intolerances: String?
    ): SearchApiRes = cookItApiService.searchRecipes(
        apiKey = apiKey,
        query = query,
        cuisine = cuisine,
        diet = diet,
        type = type,
        intolerances = intolerances
    )


    override suspend fun getRecipeInfo(
        apiKey: String,
        id: Long,
        includeNutrition: Boolean
    ): Recipe = cookItApiService.getRecipeInfo(apiKey = apiKey, id = id, includeNutrition = false)

    override suspend fun getRandomRecipes(
        apiKey: String,
        limitLicense: Boolean?,
        tags: List<String>?,
        number: Int
    ): RandomRecipesAPIRes = cookItApiService.getRandomRecipes(
        apiKey = apiKey,
        limitLicense = limitLicense,
        tags = tags,
        number = number
    )
}