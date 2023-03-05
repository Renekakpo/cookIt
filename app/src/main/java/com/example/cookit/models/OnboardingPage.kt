package com.example.cookit.models

import com.example.cookit.R

data class OnboardingPage(val titleResId: Int, val descriptionResId: Int, val imageResId: Int)

val onboardingPages = listOf(
    OnboardingPage(
        titleResId = R.string.onboarding_welcome_page_title,
        descriptionResId = R.string.onboarding_welcome_page_description,
        imageResId = R.drawable.onboarding_welcome
    ),
    OnboardingPage(
        titleResId = R.string.onboarding_search_page_title,
        descriptionResId = R.string.onboarding_search_page_description,
        imageResId = R.drawable.onboarding_search
    ),
    OnboardingPage(
        titleResId = R.string.onboarding_favorites_page_title,
        descriptionResId = R.string.onboarding_favorites_page_description,
        imageResId = R.drawable.onboarding_favorites
    ),
    OnboardingPage(
        titleResId = R.string.onboarding_cooking_page_title,
        descriptionResId = R.string.onboarding_cooking_page_description,
        imageResId = R.drawable.onboarding_cooking
    )
)
