package com.example.cookit.models.converters

import androidx.room.TypeConverter

object StringListConverters {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(str: String): List<String> {
        return str.split(",")
    }
}