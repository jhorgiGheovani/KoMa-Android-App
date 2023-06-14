package com.jhorgi.registermenu.ui.screen.register

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
import com.jhorgi.registermenu.R
import com.jhorgi.registermenu.components.TextFieldLabel
import com.jhorgi.registermenu.di.Injection
import com.jhorgi.registermenu.model.GenericResponse
import com.jhorgi.registermenu.model.RegisterRequestBody
import com.jhorgi.registermenu.ui.UiState
import com.jhorgi.registermenu.ui.ViewModelFactory

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
//        TextFieldName(textInput = nameInput, onTextChanged = { newName -> nameInput = newName })
//        Spacer(modifier = Modifier.height(8.dp))
//        TextFieldEmail(textInput = email, onTextChanged = { newEmail -> email = newEmail })
//        ErrorMsg(isEmailValid, "Email Not Valid")
//        Spacer(modifier = Modifier.height(8.dp))
//        TextFieldPassword(
//            textInput = passwordInput,
//            onTextChanged = { newPassword -> passwordInput = newPassword })
//        Spacer(modifier = Modifier.height(15.dp))
//        Button(
//            onClick = {
//                isNameValid = nameInput.length > 2
//                isEmailValid = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//                isPasswordValid = passwordInput.length <= 6
//            },
//            content = {
//                Text(
//                    text = "Register",
//                    style = MaterialTheme.typography.h6.copy(
//                        fontWeight = FontWeight.Bold
//                    ),
//                    color = Color.White
//                )
//            },
//            shape = RoundedCornerShape(10.dp),
//            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary_color)),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            enabled = isValueEmpty(nameInput, email, passwordInput)
//        )




        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Already have an account?",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 14.sp
            )
            Text(
                text = "Login",
                modifier = Modifier
                    .padding(start = 3.dp)
                    .clickable { navigateToLogin() },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary_color),
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

    }

}



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
    var isButtonClicked by remember {mutableStateOf(false)}
    var nameInput by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var passwordInput by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isNameValid by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    val context = LocalContext.current


    Column {

        TextFieldLabel("Name")
        TextField(
            value = nameInput,
            onValueChange = { newText ->
                nameInput = newText
            },
            placeholder = { Text(text = "Enter Name") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_input_filed_register_name),
                    contentDescription = "name input register input field"
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
        //Email Input
        TextFieldLabel(text = "Email")
        TextField(
            value = email,
            onValueChange = { newText ->
                email = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text(text = "Enter Email") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_input_field_register_email),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text(text = "Enter Password") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_input_filed_register_password),
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

        Spacer(modifier = Modifier.height(25.dp))


        //Button Login
        Button(
            onClick = {
                isNameValid = nameInput.length<2
                isEmailValid = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

                isPasswordValid = passwordInput.length < 8

                if(!isNameValid && !isEmailValid && !isPasswordValid){
                    isButtonClicked = true
                }
            },
            content = {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary_color)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isValueEmpty(nameInput,email,passwordInput)
        )


    }

    if (isButtonClicked) {
        when (register) {
            is UiState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is UiState.Success -> {
                val response = (register as UiState.Success<GenericResponse>).data
                Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
                navigateToLogin()
                isButtonClicked=false
            }
            is UiState.Error -> {
                isButtonClicked=false
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
//            viewModel.register(nameInput, email, passwordInput)

            viewModel.register(RegisterRequestBody("sasasa2@gmail.com", "password","testing", "male"))
        }
    }


}

fun isValueEmpty(name:String , email: String, password: String): Boolean {
    var check = true

    if (email.isEmpty() || password.isEmpty() ||name.isEmpty()) {
        check = false
    }
    return check
}


