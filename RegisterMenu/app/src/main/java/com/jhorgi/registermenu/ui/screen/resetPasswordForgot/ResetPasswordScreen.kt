package com.jhorgi.registermenu.ui.screen.resetPasswordForgot

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.registermenu.R
import com.jhorgi.registermenu.components.ErrorMessageTextField
import com.jhorgi.registermenu.components.TextFieldLabel
import com.jhorgi.registermenu.components.TextFieldPassword
import com.jhorgi.registermenu.di.Injection
import com.jhorgi.registermenu.model.GenericResponse
import com.jhorgi.registermenu.model.ResetPasswordRequestBody
import com.jhorgi.registermenu.ui.UiState
import com.jhorgi.registermenu.ui.ViewModelFactory


@Composable
fun ResetPasswordScreen(
    key: String,
    viewModel: ResetPasswordViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToLogin: () -> Unit
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var errorMsg by rememberSaveable { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isConfirmPasswordValid by remember { mutableStateOf(false) }
    var isButtonClicked by remember { mutableStateOf(false) }

    val resetPasswordLiveData by viewModel.resetPasswordLiveData.observeAsState(initial = UiState.Loading)


    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .verticalScroll(state = rememberScrollState(), enabled = true),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column {
            TextFieldLabel(text = "Password")
            TextFieldPassword(
                textInput = password,
                onTextChanged = { newValue -> password = newValue })
            ErrorMessageTextField(boolean = isPasswordValid, errorText = errorMsg)

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldLabel(text = "Confirm Password")
            TextFieldPassword(
                textInput = confirmPassword,
                onTextChanged = { newValue -> confirmPassword = newValue },
                placeholder = "Enter Password Confirmation"
            )
            ErrorMessageTextField(boolean = isConfirmPasswordValid, errorText = errorMsg)
        }
        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {

                if (password.length < 8) {
                    isPasswordValid = true
                    errorMsg = "Password should contain minimum 8 characters"
                } else if (confirmPassword != password) {
                    isConfirmPasswordValid = true
                    isPasswordValid = false
                    errorMsg = "Password Confirmation not match with Password"
                } else {
                    isConfirmPasswordValid = false
                    isPasswordValid = false
                }

                if (!isPasswordValid && !isConfirmPasswordValid) {
                    isButtonClicked = true
                }

            },
            content = {
                Text(
                    text = "Change Password",
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary_color)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isValueEmpty(password, confirmPassword)
        )


    }

    if (isButtonClicked) {
        when (resetPasswordLiveData) {
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                val response = (resetPasswordLiveData as UiState.Success<GenericResponse>).data
                Toast.makeText(LocalContext.current, response.message, Toast.LENGTH_SHORT).show()
                isButtonClicked = false
                navigateToLogin()
            }
            is UiState.Error -> {
                isButtonClicked = false
                Log.d("ErrorResetPassword", "ErrorResetPassword")
                Toast.makeText(LocalContext.current, "ErrorResetPassword ", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.resetPassword(ResetPasswordRequestBody(key, password,confirmPassword))
        }
    }
}

fun isValueEmpty(email: String, confirmEmail: String): Boolean {
    var check = true

    if (email.isEmpty() || confirmEmail.isEmpty()) {
        check = false
    }
    return check
}