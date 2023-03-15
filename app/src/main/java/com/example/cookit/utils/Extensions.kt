package com.example.cookit.utils

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cookit.app.CookIt

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [CookIt].
 */
fun CreationExtras.cookItApplication(): CookIt =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CookIt)
