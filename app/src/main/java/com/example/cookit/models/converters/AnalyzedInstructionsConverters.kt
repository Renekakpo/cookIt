package com.example.cookit.models.converters

import androidx.room.TypeConverter
import com.example.cookit.models.AnalyzedRecipeInstructionsApiRes
import com.example.cookit.utils.appJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class AnalyzedInstructionsConverters {
    /**
     * Convert a list of [AnalyzedRecipeInstructionsApiRes] to a Json string.
     */
    @TypeConverter
    fun fromTypesJson(analyzedRecipeInstructionsApiRes: List<AnalyzedRecipeInstructionsApiRes>?): String {
        return appJson.encodeToString(analyzedRecipeInstructionsApiRes ?: emptyList())
    }

    /**
     * Convert a Json string to a list of [AnalyzedRecipeInstructionsApiRes].
     * Best-effort: returns an empty list on unreadable/legacy data.
     */
    @TypeConverter
    fun toTypesList(jsonTypes: String?): List<AnalyzedRecipeInstructionsApiRes> {
        if (jsonTypes.isNullOrEmpty()) return emptyList()
        return try {
            appJson.decodeFromString(jsonTypes)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
