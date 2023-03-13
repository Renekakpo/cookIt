package com.example.cookit.network

import com.example.cookit.models.RecipeSearchResult
import com.example.cookit.models.network.SpecificRecipeInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CookItApiService {
    @GET("recipes/v2")
    suspend fun searchRecipes(
        @Query("type") type: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("q") input: String,
        @Query("igr") ingredient: String?,
        @Query("diet") diet: List<String>?,
        @Query("health") health: List<String>?,
        @Query("cuisineType") cuisineType: List<String>?,
        @Query("dishType") dishType: List<String>?,
        @Query("mealType") mealType: List<String>?,
    ): RecipeSearchResult

    @GET("recipes/v2/{id}")
    suspend fun getSpecificRecipeInfo(
        @Path("id") id: String,
        @Query("type") type: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String
    ): SpecificRecipeInfo
}