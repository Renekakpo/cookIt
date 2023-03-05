package com.example.cookit.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookit.R
import com.example.cookit.ui.theme.CookItTheme

@Composable
fun LoginRegistrationScreen() {
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
        Text(
            text = if (isRegistration) stringResource(R.string.registration_text) else stringResource(
                R.string.login_text
            ),
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary
        )

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
            visualTransformation = PasswordVisualTransformation(),
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

        if (isRegistration) {
            AnimatedVisibility(
                visible = isRegistration,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it }),
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
                    visualTransformation = PasswordVisualTransformation(),
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle login/registration here */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = if (isRegistration) stringResource(R.string.register_text) else stringResource(
                    id = R.string.login_text
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

@Preview(showBackground = true)
@Composable
fun LoginRegistrationScreenPreview() {
    CookItTheme {
        LoginRegistrationScreen()
    }
}