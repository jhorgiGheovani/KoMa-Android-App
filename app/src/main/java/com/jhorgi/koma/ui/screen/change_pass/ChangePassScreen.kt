package com.jhorgi.koma.ui.screen.change_pass

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.jhorgi.koma.R
import com.jhorgi.koma.data.remote.response.ChangePasswordRequestBody
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.ErrorMessageTextField
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem
import com.jhorgi.koma.ui.components.TextFieldPassword

@Composable
fun ChangePassScreen(
    viewModel: ChangePassViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToProfile: () -> Unit,
)
{

    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isNewPasswordValid by remember { mutableStateOf(false) }
    var isConfirmPasswordValid by remember { mutableStateOf(false) }
    var isButtonClicked by remember { mutableStateOf(false) }
    val changePassLiveData by viewModel.changePassLiveData.observeAsState(initial = UiState.Loading)
    var errorMsg by rememberSaveable { mutableStateOf("") }

    val token = viewModel.getToken(LocalContext.current)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "Update Password",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldPassword(textInput = oldPassword, onTextChanged = { newValue -> oldPassword = newValue}, placeholder = "Enter old password")
        Spacer(modifier = Modifier.height(10.dp))
        TextFieldPassword(textInput = newPassword, onTextChanged = { newValue -> newPassword = newValue }, placeholder = "Enter New Password")
        ErrorMessageTextField(boolean = isNewPasswordValid, errorText = errorMsg)
        Spacer(modifier = Modifier.height(10.dp))
        TextFieldPassword(textInput = confirmPassword, onTextChanged = { newValue -> confirmPassword = newValue }, placeholder = "Enter Password Confirmation")
        ErrorMessageTextField(boolean = isConfirmPasswordValid, errorText = errorMsg)
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (newPassword.length < 8) {
                    isNewPasswordValid = true
                    errorMsg = "Password should contain minimum 8 characters"
                } else if (confirmPassword != newPassword) {
                    isConfirmPasswordValid = true
                    isNewPasswordValid = false
                    errorMsg = "Password Confirmation not match with Password"
                } else {
                    isConfirmPasswordValid = false
                    isNewPasswordValid = false
                }

                if (!isNewPasswordValid && !isConfirmPasswordValid) {
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
            enabled = isValueEmpty(oldPassword,newPassword, confirmPassword)
        )


        if (isButtonClicked) {
            when (changePassLiveData) {
                is UiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .padding(15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LottieLoadingAuthItem(modifier = Modifier)
                        }
                    }

                }
                is UiState.Success -> {
                    val response = (changePassLiveData as UiState.Success<GenericResponse>).data
                    navigateToProfile()
                    Toast.makeText(LocalContext.current, response.message, Toast.LENGTH_SHORT).show()
                    isButtonClicked = false
                }
                is UiState.Error -> {
                    isButtonClicked = false
                }
            }

            LaunchedEffect(Unit) {
                viewModel.changePaasword(ChangePasswordRequestBody(oldPassword,newPassword,confirmPassword),"Bearer ${token.toString()}" )
            }
        }



    }

}

fun isValueEmpty(oldPassword: String, newPassword: String, confirmEmail: String): Boolean {
    var check = true

    if (oldPassword.isEmpty() ||newPassword.isEmpty() || confirmEmail.isEmpty()) {
        check = false
    }
    return check
}