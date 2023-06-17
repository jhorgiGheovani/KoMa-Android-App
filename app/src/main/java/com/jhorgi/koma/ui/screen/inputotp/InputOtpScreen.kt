package com.jhorgi.koma.ui.screen.inputotp

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.jhorgi.koma.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.data.remote.response.otp.InputOtpRespose
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputOtpScreen(

    viewModel: InputOtpViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToResetPassword: (String) -> Unit,
    onOtpChanged: (String) -> Unit

) {
    var isButtonClicked by remember { mutableStateOf(false) }
    val otpLiveData by viewModel.inputOtpLiveData.observeAsState(initial = UiState.Loading)
    val otpLength = remember { 5 }
    val keyboardController = LocalSoftwareKeyboardController.current

    var otpValue by rememberSaveable{
        mutableStateOf("")
    }


    val focusRequester = remember {
        FocusRequester()
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Confirm OTP",
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ))
        Spacer(modifier = Modifier.height(20.dp))
        BasicTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = otpValue,
            onValueChange = { value ->
                if (value.length <= otpLength) {
                    otpValue = value
                    onOtpChanged(value)
                }
            },
            decorationBox = {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    repeat(otpLength) { index ->
                        // 6
                        // 4 5 2 <<< otpValue = "452
                        val char = when {
                            index == otpValue.length -> ""
                            index >= otpValue.length -> ""
                            else -> otpValue[index].toString()
                        }
                        OtpCell(
                            char = char,
                            modifier = Modifier.weight(
                                1f
                            )
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                isButtonClicked = true
            },
            content = {
                Text(
                    text = "Send Otp",
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
        )
        if (isButtonClicked) {
            when (otpLiveData) {
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
                    val response = (otpLiveData as UiState.Success<InputOtpRespose>).data
                    navigateToResetPassword(response.key)
                    Toast.makeText(LocalContext.current, response.message, Toast.LENGTH_SHORT).show()
                    isButtonClicked = false
                }
                is UiState.Error -> {
//                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
                    isButtonClicked = false
                }
            }

            LaunchedEffect(Unit) {
                viewModel.inputOtp(otpValue)
            }
        }
        LaunchedEffect(key1 = true) {
            focusRequester.requestFocus()
            keyboardController?.show()

        }
    }



}

@Composable
fun OtpCell(
    char: String,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .aspectRatio(2f)
            .border(width = 0.5.dp, color = Color.Black, shape = MaterialTheme.shapes.small)
    ) {
        Text(
            text = char,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.wrapContentSize(align = Alignment.Center)
        )
    }
}

