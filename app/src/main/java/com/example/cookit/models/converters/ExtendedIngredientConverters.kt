package com.example.cookit.models.converters

import androidx.room.TypeConverter
import com.example.cookit.models.ExtendedIngredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ExtendedIngredientConverters {
    /**
     * Convert a a list of [ExtendedIngredient] to a Json
     */
    @TypeConverter
    fun fromTypesJson(extendedIngredient: List<ExtendedIngredient>?): String {
        return Gson().toJson(extendedIngredient) ?: ""
    }

    /**
     * Convert a json to a list of [ExtendedIngredient]
     */
    @TypeConverter
    fun toTypesList(jsonTypes: String?): List<ExtendedIngredient> {
        val notesType = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return Gson().fromJson(jsonTypes, notesType) ?: listOf()
    }
}