package com.example.cookit.data.network

import com.example.cookit.network.CookItApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface CookItNetworkContainer {
    val cookItNetworkRepository: CookItNetworkRepository
}

@OptIn(ExperimentalSerializationApi::class)
class DefaultCookItNetworkContainer : CookItNetworkContainer {
    // Spoonacular API base url
    private val baseUrl = "https://api.spoonacular.com/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val jsonConverter = json.asConverterFactory("application/json".toMediaType())

    // Retrofit built instance
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(jsonConverter)
        .baseUrl(baseUrl).build()

    // Create a retrofit service out of retrofit instance
    private val retrofitService: CookItApiService by lazy {
        retrofit.create(CookItApiService::class.java)
    }

    override val cookItNetworkRepository: CookItNetworkRepository by lazy {
        DefaultCookItNetworkRepository(retrofitService)
    }
}