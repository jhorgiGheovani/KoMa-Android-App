package com.jhorgi.koma.ui.screen.regiter

import android.util.Patterns
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
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.data.remote.response.register.RegisterRequestBody
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem
import com.jhorgi.koma.ui.components.TextFieldLabel
import com.jhorgi.koma.ui.theme.Typography

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit
) {
//    var nameInput by rememberSaveable { mutableStateOf("") }
//    var email by rememberSaveable { mutableStateOf("") }
//    var passwordInput by rememberSaveable { mutableStateOf("") }
//    var isNameValid by remember { mutableStateOf(false) }
//    var isEmailValid by remember { mutableStateOf(false) }
//    var isPasswordValid by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(state = rememberScrollState(), enabled = true)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
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
        //Input Field
        InputRegisterField(navigateToLogin)
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Already have an account?",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "Login",
                modifier = Modifier
                    .padding(start = 3.dp)
                    .clickable { navigateToLogin() },
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun InputRegisterField(
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
) {
    val register by viewModel.registerLiveData.observeAsState(initial = UiState.Loading)
    var isButtonClicked by remember { mutableStateOf(false) }
    var nameInput by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var passwordInput by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isNameValid by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var genderInput by remember { mutableStateOf("") }


    val context = LocalContext.current

    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            TextFieldLabel("Name")
            TextField(
                value = nameInput,
                onValueChange = { newText ->
                    nameInput = newText
                },
                placeholder = {
                    Text(
                        text = "Enter Name",
                        style = MaterialTheme.typography.caption
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.user),
                        contentDescription = "name input register input field"
                    )
                },
                textStyle = Typography.body1.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal

                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(7.dp)),

                )
            if (isNameValid) {
                Text(
                    text = "Name minimum 2 characters",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            //Email Input
            TextFieldLabel(text = "Email")
            TextField(
                value = email,
                onValueChange = { newText ->
                    email = newText
                },
                textStyle = Typography.body1.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal

                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeholder = {
                    Text(
                        text = "Enter Email",
                        style = MaterialTheme.typography.caption
                    )
                },
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
                placeholder = {
                    Text(
                        text = "Enter Password",
                        style = MaterialTheme.typography.caption
                    )
                },
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
                    .border(1.dp, Color.LightGray, RoundedCornerShape(7.dp)),
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

            TextFieldLabel(text = "Gender")
            ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it })
            {
                TextField(
                    value = genderInput,
                    placeholder = {
                        Text(
                            text = "Choose Gender",
                            style = MaterialTheme.typography.caption
                        )
                    },
                    readOnly = true,
                    textStyle = Typography.body1.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    onValueChange = {},
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_dropdown),
                            contentDescription = "Dropdown menu"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_gender),
                            contentDescription = "Gender Logo"
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, RoundedCornerShape(7.dp)),

                    )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            genderInput = "Male"
                            isExpanded = false
                        }
                    ) {
                        Text(
                            text = "Male",
                            style = MaterialTheme.typography.body2.copy(
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            genderInput = "Female"
                            isExpanded = false
                        }
                    ) {
                        Text(
                            text = "Female",
                            style = MaterialTheme.typography.body2.copy(
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
            //Button Login
            Button(
                onClick = {

                    isNameValid = nameInput.length < 2
                    isEmailValid = !Patterns.EMAIL_ADDRESS.matcher(email).matches()

                    isPasswordValid = passwordInput.length < 8

                    if (!isNameValid && !isEmailValid && !isPasswordValid) {
                        isButtonClicked = true
                    }
                },
                content = {
                    Text(
                        text = "Register",
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
                enabled = isValueEmpty(nameInput, email, passwordInput, genderInput)
            )
        }
        if (isButtonClicked) {
            when (register) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.width(80.dp).height(80.dp).padding(15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieLoadingAuthItem(modifier = Modifier)
                    }
                }
                is UiState.Success -> {
                    val response = (register as UiState.Success<GenericResponse>).data
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                    isButtonClicked = false
                }

                is UiState.Error -> {
                    isButtonClicked = false
                    Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
                }
            }
            LaunchedEffect(Unit) {
                viewModel.register(RegisterRequestBody(email, passwordInput, nameInput, genderInput))
            }
        }
    }

}

fun isValueEmpty(name: String, email: String, password: String, gender:String): Boolean {
    var check = true

    if (email.isEmpty() || password.isEmpty() || name.isEmpty() || gender.isEmpty()) {
        check = false
    }
    return check
}
