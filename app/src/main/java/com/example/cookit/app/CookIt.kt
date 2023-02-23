package com.example.cookit.app

import android.app.Application
import com.example.cookit.data.network.CookItNetworkContainer
import com.example.cookit.data.network.DefaultCookItNetworkContainer
import com.example.cookit.data.offline.CookItAppContainer
import com.example.cookit.data.offline.CookItOfflineDataContainer

class CookIt: Application() {

    /**
     * [CookItAppContainer] instance used by the rest of classes to obtain dependencies
     */
    lateinit var offlineDataContainer: CookItAppContainer
    /**
     * [CookItNetworkContainer] instance used by the rest of classes to obtain dependencies
     */
    lateinit var networkDataContainer: CookItNetworkContainer

    override fun onCreate() {
        super.onCreate()
        offlineDataContainer = CookItOfflineDataContainer(this)
        networkDataContainer = DefaultCookItNetworkContainer()
    }
}