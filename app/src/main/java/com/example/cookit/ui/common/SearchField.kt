package com.example.cookit.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun SearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    onFilterClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Transparent)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(5.dp))
        TextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.body1,
            placeholder = {
                Text(
                    text = "Search",
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        IconButton(
            onClick = onFilterClicked
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                tint = MaterialTheme.colors.surface
            )
        }
    }
}

@Preview
@Composable
fun SearchFieldPreview() {
    CookItTheme {
        SearchField(query = "diner", onQueryChanged = {}, onFilterClicked = {})
    }
}