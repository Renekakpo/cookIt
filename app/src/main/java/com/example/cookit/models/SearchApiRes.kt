package com.example.cookit.models

data class SearchApiRes(
    val offset: Int?,
    val number: Int?,
    val results: List<Recipe>?
)