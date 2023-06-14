package com.example.cookit.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cookit.BuildConfig
import com.example.cookit.R
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsItemScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    val favoriteItemCount: Int by viewModel.getCountOfItems().collectAsState(initial = 0)

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetElevation = 8.dp,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(
            topStart = 25.dp,
            topEnd = 25.dp
        ),
        sheetContent = {
            AppInfoSheet()
        }
    ) {
        SettingsMenuMainContainer(
            modifier = modifier,
            favoriteItemCount = favoriteItemCount,
            cookedDataCount = 0,
            onInfoClick = {
                coroutineScope.launch { modalSheetState.show() }
            },
            onGitHubClick = {}
        )
    }

}

@Composable
fun SettingsMenuMainContainer(
    modifier: Modifier = Modifier,
    favoriteItemCount: Int = 0,
    cookedDataCount: Int = 0,
    onInfoClick: () -> Unit = {},
    onGitHubClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
            .background(color = colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier.align(alignment = Alignment.End),
            onClick = onInfoClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_about_app),
                contentDescription = stringResource(R.string.about_app_text),
                tint = colors.primary
            )
        }

        Spacer(modifier = Modifier.height(height = 5.dp))

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(R.drawable.onboarding_favorites)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.user_profile_picture_description),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            modifier = Modifier
                .size(90.dp)
                .clip(shape = CircleShape),
            alignment = Alignment.TopEnd
        )

        Spacer(modifier = Modifier.height(height = 15.dp))

        OutlinedTextField(
            value = "Lorem Ipsum",
            singleLine = true,
            readOnly = true,
            enabled = false,
            textStyle = typography.subtitle1,
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(R.string.fullName),
                    color = colors.onBackground.copy(alpha = 0.3f)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colors.onBackground.copy(alpha = 0.3f),
                unfocusedBorderColor = colors.onBackground.copy(alpha = 0.3f)
            ),
            shape = MaterialTheme.shapes.small
        )

        Spacer(modifier = Modifier.height(height = 15.dp))

        OutlinedTextField(
            value = "loremipsum@example.com",
            singleLine = true,
            readOnly = true,
            enabled = false,
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(R.string.email_text),
                    color = colors.onBackground.copy(alpha = 0.3f)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colors.onBackground.copy(alpha = 0.3f),
                unfocusedBorderColor = colors.onBackground.copy(alpha = 0.3f)
            ),
            shape = MaterialTheme.shapes.small
        )

        Spacer(modifier = Modifier.height(height = 15.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        border = BorderStroke(
                            width = 2.dp,
                            color = colors.primary.copy(alpha = 0.3f)
                        ),
                        shape = MaterialTheme.shapes.small
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.total_favorites_text),
                    color = colors.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    text = "$favoriteItemCount",
                    color = colors.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        border = BorderStroke(
                            width = 2.dp,
                            color = colors.primary.copy(alpha = 0.3f)
                        ),
                        shape = MaterialTheme.shapes.small
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.total_cooked_text),
                    color = colors.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    text = "$cookedDataCount",
                    color = colors.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }

    }
}

@Composable
fun AppInfoSheet() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val halfScreenHeight: Dp = screenHeight / 2

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(halfScreenHeight)
            .padding(horizontal = 30.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(5.dp)
                .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
                .align(alignment = Alignment.CenterHorizontally)
        ) {}

        Spacer(modifier = Modifier.height(15.dp))

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(R.drawable.onboarding_favorites)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.user_profile_picture_description),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            modifier = Modifier
                .size(80.dp)
                .clip(shape = CircleShape),
            alignment = Alignment.TopEnd
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.app_info),
            style = typography.body1,
            color = colors.onBackground.copy(alpha = 0.5f),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(R.string.version_name_text, BuildConfig.VERSION_NAME),
            style = typography.body1,
            color = colors.onBackground.copy(alpha = 0.5f),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun AppInfoSheetPreview() {
    CookItTheme {
        AppInfoSheet()
    }
}