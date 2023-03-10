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
    // Edamam API base url
    private val BASE_URL = "https://api.edamam.com/api/"

    // Retrofit built instance
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).build()

    // Create a retrofit service out of retrofit instance
    private val retrofitService: CookItApiService by lazy {
        retrofit.create(CookItApiService::class.java)
    }

    override val cookItNetworkRepository: CookItNetworkRepository by lazy {
        DefaultCookItNetworkRepository(retrofitService)
    }
}