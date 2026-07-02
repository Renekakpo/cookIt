package com.example.cookit.models.converters

import androidx.room.TypeConverter
import com.example.cookit.models.ExtendedIngredient
import com.example.cookit.utils.appJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class ExtendedIngredientConverters {
    /**
     * Convert a list of [ExtendedIngredient] to a Json string.
     */
    @TypeConverter
    fun fromTypesJson(extendedIngredient: List<ExtendedIngredient>?): String {
        return appJson.encodeToString(extendedIngredient ?: emptyList())
    }

    /**
     * Convert a Json string to a list of [ExtendedIngredient].
     * Best-effort: returns an empty list on unreadable/legacy data.
     */
    @TypeConverter
    fun toTypesList(jsonTypes: String?): List<ExtendedIngredient> {
        if (jsonTypes.isNullOrEmpty()) return emptyList()
        return try {
            appJson.decodeFromString(jsonTypes)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
