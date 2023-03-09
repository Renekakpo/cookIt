package com.example.cookit.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun CustomLoaderScreen(
    modifier: Modifier = Modifier,
    loaderSize: Dp = 25.dp,
    loaderColor: Color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .size(loaderSize)
            .background(Color.Transparent)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(loaderSize)
                .rotate(rotation)
                .align(Alignment.Center),
            color = loaderColor,
            strokeWidth = 2.dp
        )
    }
}

@Preview
@Composable
fun CustomLoaderScreenPreview() {
    CookItTheme {
        CustomLoaderScreen()
    }
}