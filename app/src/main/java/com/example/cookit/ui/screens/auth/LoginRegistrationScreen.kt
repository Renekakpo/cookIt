package com.example.cookit.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cookit.R
import com.example.cookit.navigation.BottomNavGraph
import com.example.cookit.navigation.NavDestination
import com.example.cookit.ui.theme.CookItTheme
import com.example.cookit.utils.AppViewModelProvider

object LoginRegistrationScreen: NavDestination {
    override val route: String = "authentication_screen"
}

@Composable
fun LoginRegistrationScreen(navController: NavController, viewModel: LoginRegistrationViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    var emailState by rememberSaveable { mutableStateOf("") }
    var passwordState by rememberSaveable { mutableStateOf("") }
    var confirmPasswordState by rememberSaveable { mutableStateOf("") }
    var isRegistration by rememberSaveable { mutableStateOf(false) }
    var passwordVisibilityState by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisibilityState by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                if (isRegistration) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.signup_text),
                        style = MaterialTheme.typography.h2,
                        color = Color.DarkGray
                    )
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.signup_subtitle_text),
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.login_greeting_text),
                        style = MaterialTheme.typography.h1,
                        color = Color.DarkGray
                    )
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.login_subtitle_text),
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = emailState,
            singleLine = true,
            onValueChange = { emailState = it },
            label = {
                Text(
                    text = stringResource(R.string.email_text),
                    color = MaterialTheme.colors.secondary
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.email_field_placeholder),
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_email),
                    contentDescription = stringResource(R.string.email_field_icon_description),
                    tint = MaterialTheme.colors.secondary
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = Color.LightGray
            ),
            shape = MaterialTheme.shapes.small
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = passwordState,
            singleLine = true,
            onValueChange = { passwordState = it },
            label = {
                Text(
                    text = stringResource(R.string.password_text),
                    color = MaterialTheme.colors.secondary
                )
            },
            visualTransformation = if (passwordVisibilityState) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.password_field_placeholder),
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_password),
                    contentDescription = stringResource(R.string.password_field_icon_description),
                    tint = MaterialTheme.colors.secondary
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibilityState = !passwordVisibilityState
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisibilityState) {
                            Icons.Rounded.Visibility
                        } else {
                            Icons.Rounded.VisibilityOff
                        },
                        contentDescription = stringResource(R.string.password_field_trailing_icon_description),
                        tint = MaterialTheme.colors.primary
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = Color.LightGray
            ),
            shape = MaterialTheme.shapes.small
        )

        AnimatedVisibility(
            visible = isRegistration,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it }),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPasswordState,
                singleLine = true,
                onValueChange = { confirmPasswordState = it },
                label = {
                    Text(
                        text = stringResource(R.string.confirm_password_text),
                        color = MaterialTheme.colors.primary
                    )
                },
                visualTransformation = if (confirmPasswordVisibilityState) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.confirm_password_field_placeholder),
                        color = Color.LightGray
                    )
                },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.ic_password),
                        contentDescription = stringResource(R.string.confirm_password_field_icon_description),
                        tint = MaterialTheme.colors.secondary
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            confirmPasswordVisibilityState = !confirmPasswordVisibilityState
                        }
                    ) {
                        Icon(
                            imageVector = if (confirmPasswordVisibilityState) {
                                Icons.Rounded.Visibility
                            } else {
                                Icons.Rounded.VisibilityOff
                            },
                            contentDescription = stringResource(R.string.confirm_password_field_trailing_icon_description),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = Color.LightGray
                ),
                shape = MaterialTheme.shapes.small
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onAuthButtonClicked(navController = navController, viewModel = viewModel) },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = if (isRegistration) stringResource(R.string.register_text) else stringResource(
                    id = R.string.login_button_text
                ),
                color = MaterialTheme.colors.onSecondary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { isRegistration = !isRegistration },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = if (isRegistration) stringResource(R.string.login_option_text) else stringResource(
                    R.string.register_option_text
                ),
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

private fun onAuthButtonClicked(navController: NavController, viewModel: LoginRegistrationViewModel) {
    viewModel.saveLoginCompletionState(isCompleted = true)
    navController.navigate(route = BottomNavGraph.route)
}

@Preview(showBackground = true)
@Composable
fun LoginRegistrationScreenPreview() {
    CookItTheme {
        LoginRegistrationScreen(navController = rememberNavController())
    }
}