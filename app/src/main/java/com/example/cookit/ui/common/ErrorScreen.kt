package com.example.cookit.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = "Error",
            tint = MaterialTheme.colors.error,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error,
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        )
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    CookItTheme {
        ErrorScreen(
            errorMessage = "Failed to retrieve data. Kindly check your internet connexion.",
            onRetry = {}
        )
    }
}