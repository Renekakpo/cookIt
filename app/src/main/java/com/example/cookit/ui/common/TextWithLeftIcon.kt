package com.example.cookit.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun TextWithLeftIcon(
    modifier: Modifier = Modifier,
    text: String,
    isTimeIcon: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isTimeIcon) Icons.Outlined.AccessTime else Icons.Outlined.StarOutline,
            contentDescription = if (isTimeIcon) "Time Icon" else "Star Icon",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = text,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
fun TextWithLeftIconPreview() {
    CookItTheme {
        TextWithLeftIcon(modifier = Modifier, text = "4.5", isTimeIcon = false)
    }
}