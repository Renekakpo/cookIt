package com.example.cookit.utils

import java.time.LocalTime
import java.util.*

fun getGreetingText(username: String): String {
    val cal = Calendar.getInstance()
    val timeString = when (cal.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Morning"
        in 12..15 -> "Afternoon"
        else -> "Evening"
    }
    return "Good $timeString, $username!"
}

fun getFoodSuggestion(): String {
    val cal = Calendar.getInstance()
    return when (cal.get(Calendar.HOUR_OF_DAY)) {
        in 0..10 -> "What would you like to have for breakfast today?"
        in 11..14 -> "What would you like to have for lunch today?"
        in 15..18 -> "What would you like to have for a snack today?"
        else -> "What would you like to have for dinner today?"
    }
}





