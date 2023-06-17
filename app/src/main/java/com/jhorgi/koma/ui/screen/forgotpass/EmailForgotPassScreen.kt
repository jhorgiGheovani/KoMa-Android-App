package com.jhorgi.koma.ui.screen.forgotpass

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.EmailErrorMessage
import com.jhorgi.koma.ui.components.LottieEmptyItem
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem
import com.jhorgi.koma.ui.theme.Typography

@Composable
fun InputEmailForgotPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: EmailForgotPasswordViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToInputOTP: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isButtonClicked by remember { mutableStateOf(false) }
    val emailLiveData by viewModel.emailForgotPasswordLiveData.observeAsState(initial = UiState.Loading)

    Column(
        modifier = modifier
            .padding(top = 100.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(text = "Confirm Email",
        style = MaterialTheme.typography.h1.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ))
        Spacer(modifier = Modifier.height(20.dp))
        Column {
//            TextFieldLabel(text = "Email")
            EmailInputField(textInput = email, onTextInputChange = { newValue -> email = newValue })
            EmailErrorMessage(isEmailValid = isEmailValid)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                isEmailValid = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                if (!isEmailValid) {
                    isButtonClicked = true
                }

            },
            content = {
                Text(
                    text = "Send OTP",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    color = Color.White
                )
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary_color)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isValueEmpty(email)
        )
        if (isButtonClicked) {
            when (emailLiveData) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.width(80.dp).height(80.dp).padding(15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieLoadingAuthItem(modifier = Modifier)
                    }
                }
                is UiState.Success -> {
                    val response = (emailLiveData as UiState.Success<GenericResponse>).data
                    Toast.makeText(LocalContext.current, response.message, Toast.LENGTH_SHORT).show()
                    navigateToInputOTP()
                    isButtonClicked = false
                }
                is UiState.Error -> {
                    isButtonClicked = false
                    Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            LaunchedEffect(Unit) {
                viewModel.requestOtp(email)
            }
        }



    }


}


@Composable
private fun EmailInputField(textInput: String, onTextInputChange: (String) -> Unit) {


    TextField(
        value = textInput,
        onValueChange = onTextInputChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = { Text(text = "Enter Email",
            style = MaterialTheme.typography.caption) },
        textStyle = Typography.body1.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal

        ) ,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(7.dp))
    )

}

fun isValueEmpty(email: String): Boolean {
    var check = true

    if (email.isEmpty()) {
        check = false
    }
    return check
}