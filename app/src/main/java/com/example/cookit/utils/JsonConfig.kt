package com.example.cookit.utils

import kotlinx.serialization.json.Json

/**
 * Shared kotlinx-serialization [Json] instance reused across the app:
 * the network layer, Room TypeConverters and DataStore.
 *
 * `ignoreUnknownKeys = true` keeps deserialization resilient to extra fields
 * (API additions and legacy Gson-written cache entries).
 */
val appJson: Json = Json { ignoreUnknownKeys = true }
