package com.example.cookit.utils

import android.content.Context
import android.widget.Toast
import java.util.*

fun getGreetingText(username: String): String {
    val cal = Calendar.getInstance()
    val timeString = when (cal.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Morning"
        in 12..15 -> "Afternoon"
        else -> "Evening"
    }

    return if (username.isNotEmpty()) "Good $timeString, $username!" else "Good $timeString!"
}

fun getFoodSuggestion(): String {
    val cal = Calendar.getInstance()
    return when (cal.get(Calendar.HOUR_OF_DAY)) {
        in 0..10 -> "breakfast"
        in 11..14 -> "lunch"
        in 15..18 -> "a snack"
        else -> "dinner"
    }
}

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}






