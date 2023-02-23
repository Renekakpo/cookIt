package com.example.cookit.models.network

import com.example.cookit.models.Recipe

data class RecipeSearchResult(
    val from: Int,
    val to: Int,
    val count: Int,
    val _links: Links?,
    val hits: List<Hit>?
)

data class Links(
    val self: Link,
    val next: Link
)

data class Link(
    val href: String,
    val title: String
)

data class Hit(
    val recipe: Recipe,
    val _links: Links
)


data class Images(
    val THUMBNAIL: Image,
    val SMALL: Image,
    val REGULAR: Image,
    val LARGE: Image
)

data class Image(
    val url: String,
    val width: Int,
    val height: Int
)

data class Ingredient(
    val text: String,
    val quantity: Double,
    val measure: String,
    val food: String,
    val weight: Double,
    val foodId: String
)

data class Nutrient(
    val label: String,
    val quantity: Double,
    val unit: String
)

data class Digest(
    val label: String,
    val tag: String,
    val schemaOrgTag: String,
    val total: Double,
    val hasRDI: Boolean,
    val daily: Double,
    val unit: String,
    val sub: Map<String, Nutrient>
)