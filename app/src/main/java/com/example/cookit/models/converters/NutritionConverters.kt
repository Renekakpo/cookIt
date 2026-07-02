package com.example.cookit.models.converters

import androidx.room.TypeConverter
import com.example.cookit.models.Nutrition
import com.example.cookit.utils.appJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class NutritionConverters {
    /**
     * Convert a [Nutrition] to a Json string.
     */
    @TypeConverter
    fun fromTypesJson(nutrition: Nutrition?): String {
        return if (nutrition == null) "" else appJson.encodeToString(nutrition)
    }

    /**
     * Convert a Json string to a [Nutrition].
     * Best-effort: returns null on unreadable/legacy data.
     */
    @TypeConverter
    fun toTypesList(jsonTypes: String?): Nutrition? {
        if (jsonTypes.isNullOrEmpty()) return null
        return try {
            appJson.decodeFromString<Nutrition>(jsonTypes)
        } catch (e: Exception) {
            null
        }
    }
}
