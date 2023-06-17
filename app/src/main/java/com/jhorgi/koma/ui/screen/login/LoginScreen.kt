package com.jhorgi.koma.ui.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.R
import com.jhorgi.koma.data.remote.response.login.LoginResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem
import com.jhorgi.koma.ui.components.TextFieldLabel
import com.jhorgi.koma.ui.theme.Typography

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .verticalScroll(state = rememberScrollState(), enabled = true),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.img_register),
            contentDescription = null,
            alignment = Alignment.Center,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
        )

        inputLoginField(navigateToForgotPassword, navigateToHome)
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Don't have any account?",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "Register",
                modifier = Modifier
                    .padding(start = 3.dp)
                    .clickable { navigateToRegister() },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary_color),
                fontSize = 14.sp,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun inputLoginField(
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),

    ) {

    val login by viewModel.loginLiveData.observeAsState(initial = UiState.Loading)
    var isButtonClicked by remember {mutableStateOf(false)}
    var email by rememberSaveable { mutableStateOf("") }
    var passwordInput by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if(viewModel.getToken(context).value?.isEmpty() == false){
            navigateToHome()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        //Email Input
        TextFieldLabel(
            text = "Email",
        )
        TextField(
            value = email,
            onValueChange = { newText ->
                email = newText
            },
            textStyle = Typography.body1.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal

            ) ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text(
                text = "Enter Email",
                style = MaterialTheme.typography.caption
            ) },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.atsign),
                    contentDescription = "email input register input field"
                )
            },
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

        if (isEmailValid) {
            Text(
                text = "Email Format Not Valid",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        //Password Input
        TextFieldLabel(text = "Password")
        TextField(
            value = passwordInput,
            onValueChange = { newText ->
                passwordInput = newText
            },
            textStyle = Typography.body1.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal

            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text(
                text = "Enter Password",
                style = MaterialTheme.typography.caption) },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.lock),
                    contentDescription = "password input register input field"
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    if (passwordVisible) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_eye_on),
                            contentDescription = "Hide password"
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_eye_off),
                            contentDescription = "Show password"
                        )
                    }
                }

            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp)),
        )

        if (isPasswordValid) {
            Text(
                text = "Password should contain minimum 8 character",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }


        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forgot Password? ",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp)
                .clickable { navigateToForgotPassword() },
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontSize = 14.sp,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Normal
            )
        )

        Spacer(modifier = Modifier.height(15.dp))


        //Button Login
        Button(
            onClick = {
                isEmailValid = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

                isPasswordValid = passwordInput.length < 8

                if (!isEmailValid && !isPasswordValid) {
                    isButtonClicked = true
                }

            },
            content = {
                Text(
                    text = "Login",
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
        )
    }
    if (isButtonClicked) {
        when (login) {
            is UiState.Loading -> {
                Column (
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
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
                val response = (login as UiState.Success<LoginResponse>).data
                viewModel.setToken(response.accessToken, context)
                Log.d("NavigateToHome", "NavigateToHome")
                navigateToHome()
                isButtonClicked=false
            }
            is UiState.Error -> {
                isButtonClicked=false
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.login(email, passwordInput)
        }
    }
}



