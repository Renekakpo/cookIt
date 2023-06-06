package com.example.cookit.network

import com.example.cookit.models.AnalyzedRecipeInstructionsApiRes
import com.example.cookit.models.RandomRecipesAPIRes
import com.example.cookit.models.Recipe
import com.example.cookit.models.SearchApiRes
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CookItApiService {
    @Headers("Content-Type: application/json")
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("cuisine") cuisine: String?,
        @Query("diet") diet: String?,
        @Query("type") type: String?,
        @Query("intolerances") intolerances: String?,
    ): SearchApiRes

    @Headers("Content-Type: application/json")
    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") id: Long,
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean
    ): Recipe

    @Headers("Content-Type: application/json")
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("limitLicense") limitLicense: Boolean?,
        @Query("tags") tags: List<String>?,
        @Query("number") number: Int
    ): RandomRecipesAPIRes

    @Headers("Content-Type: application/json")
    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getAnalyzedRecipeInstructions(
        @Query("apiKey") apiKey: String,
        @Query("id") id: Long,
        @Query("stepBreakdown") stepBreakdown: Boolean
    ): List<AnalyzedRecipeInstructionsApiRes>
}