package com.example.cookit.models.converters

import androidx.room.TypeConverter
import com.example.cookit.models.Nutrition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NutritionConverters {
    /**
     * Convert a a list of [Nutrition] to a Json
     */
    @TypeConverter
    fun fromTypesJson(nutrition: Nutrition?): String {
        return Gson().toJson(nutrition) ?: ""
    }

    /**
     * Convert a json to a list of [Nutrition]
     */
    @TypeConverter
    fun toTypesList(jsonTypes: String?): Nutrition? {
        val notesType = object : TypeToken<Nutrition>() {}.type
        return Gson().fromJson(jsonTypes, notesType)
    }
}