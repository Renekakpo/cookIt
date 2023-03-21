package com.example.cookit.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchApiRes(
    val offset: Int?,
    val number: Int?,
    val results: List<Recipe>?
)