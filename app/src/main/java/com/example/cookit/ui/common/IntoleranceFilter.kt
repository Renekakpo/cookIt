package com.example.cookit.ui.common

import android.system.Os.remove
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cookit.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IntoleranceFilter(
    intolerances: List<String>,
    selectedIntolerances: List<String>,
    onIntoleranceSelected: (List<String>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.intolerances_text),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(intolerances) { intolerance ->
                FilterChip(
                    selected = selectedIntolerances.contains(intolerance),
                    onClick = {
                        val newSelection = mutableListOf<String>()
                        newSelection.addAll(selectedIntolerances)
                        if (newSelection.contains(intolerance)) {
                            newSelection.remove(intolerance)
                        } else {
                            newSelection.add(intolerance)
                        }
                        onIntoleranceSelected(newSelection)
                    },
                    shape = MaterialTheme.shapes.small,
                    leadingIcon = if (selectedIntolerances.contains(intolerance)) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = stringResource(R.string.filterchip_icon_description),
                                tint = Color.White,
                                modifier = Modifier.size(size = 24.dp)
                            )
                        }
                    } else {
                        null
                    }
                ) {
                    Text(
                        text = intolerance,
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
