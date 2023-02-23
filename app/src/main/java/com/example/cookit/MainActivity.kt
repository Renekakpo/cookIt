package com.example.cookit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.cookit.ui.CookItApp
import com.example.cookit.ui.theme.CookItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookItTheme {
                CookItApp(navHostController = rememberNavController())
            }
        }
    }
}