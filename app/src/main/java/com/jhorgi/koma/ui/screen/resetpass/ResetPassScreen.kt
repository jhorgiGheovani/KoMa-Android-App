package com.jhorgi.koma.ui.screen.resetpass

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
import com.jhorgi.koma.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.data.remote.response.forgotpassword.ResetPasswordRequestBody
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.ErrorMessageTextField
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem
import com.jhorgi.koma.ui.components.TextFieldLabel
import com.jhorgi.koma.ui.components.TextFieldPassword

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
        Text(text = "Confirm New Password",
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ))
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            TextFieldLabel(text = "New Password")
            TextFieldPassword(
                textInput = password,
                onTextChanged = { newValue -> password = newValue })
            ErrorMessageTextField(boolean = isPasswordValid, errorText = errorMsg)

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldLabel(text = "Confirm Password")
            TextFieldPassword(
                textInput = confirmPassword,
                onTextChanged = { newValue -> confirmPassword = newValue },
                placeholder = "Confirm Password"
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
                    style = MaterialTheme.typography.body1.copy(
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
        if (isButtonClicked) {
            when (resetPasswordLiveData) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.width(80.dp).height(80.dp).padding(15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieLoadingAuthItem(modifier = Modifier)
                    }
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
}

fun isValueEmpty(email: String, confirmEmail: String): Boolean {
    var check = true

    if (email.isEmpty() || confirmEmail.isEmpty()) {
        check = false
    }
    return check
}