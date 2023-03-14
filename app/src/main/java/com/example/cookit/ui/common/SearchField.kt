package com.example.cookit.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.showMessage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    onFilterClicked: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequest = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.Transparent)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 8.dp)
            .focusRequester(focusRequest),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search_icon_description),
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
                    text = stringResource(R.string.search_field_placeholder),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (query.isNotEmpty()) {
                    showMessage(context = context, message = "request user input")
                }
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
            })
        )

        Spacer(modifier = Modifier.width(5.dp))

        IconButton(
            onClick = {
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
                onFilterClicked()
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                contentDescription = stringResource(R.string.filter_icon_description),
                tint = MaterialTheme.colors.surface
            )
        }
    }

    LaunchedEffect(true) {
        focusRequest.requestFocus()
    }
}

@Preview
@Composable
fun SearchFieldPreview() {
    CookItTheme {
        SearchField(query = "diner", onQueryChanged = {}, onFilterClicked = {})
    }
}