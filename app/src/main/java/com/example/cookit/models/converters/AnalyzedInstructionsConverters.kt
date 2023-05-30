package com.example.cookit.models.converters

import androidx.room.TypeConverter
import com.example.cookit.models.AnalyzedRecipeInstructionsApiRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AnalyzedInstructionsConverters {
    /**
     * Convert a a list of [AnalyzedRecipeInstructionsApiRes] to a Json
     */
    @TypeConverter
    fun fromTypesJson(analyzedRecipeInstructionsApiRes: List<AnalyzedRecipeInstructionsApiRes>?): String {
        return Gson().toJson(analyzedRecipeInstructionsApiRes) ?: ""
    }

    /**
     * Convert a json to a list of [AnalyzedRecipeInstructionsApiRes]
     */
    @TypeConverter
    fun toTypesList(jsonTypes: String?): List<AnalyzedRecipeInstructionsApiRes> {
        val notesType = object : TypeToken<List<AnalyzedRecipeInstructionsApiRes>>() {}.type
        return Gson().fromJson(jsonTypes, notesType) ?: listOf()
    }
}