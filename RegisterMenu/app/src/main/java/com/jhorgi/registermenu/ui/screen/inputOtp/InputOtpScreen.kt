package com.jhorgi.registermenu.ui.screen.inputOtp

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.registermenu.R
import com.jhorgi.registermenu.di.Injection
import com.jhorgi.registermenu.model.InputOtpRespose
import com.jhorgi.registermenu.ui.UiState
import com.jhorgi.registermenu.ui.ViewModelFactory
import com.jhorgi.registermenu.ui.theme.GreyDark
import com.jhorgi.registermenu.ui.theme.GreyLight

@Composable
fun InputOtpScreen(
    viewModel: InputOtpViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToResetPassword: (String) -> Unit,
) {

    var otpValue by rememberSaveable { mutableStateOf("") }
    var isButtonClicked by remember { mutableStateOf(false) }
    val otpLiveData by viewModel.inputOtpLiveData.observeAsState(initial = UiState.Loading)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {


        Text(text = "ENTER OTP", style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(10.dp))
        OtpTextField(
            otpText = otpValue,
            onOtpTextChange = { value, _ ->
                otpValue = value
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {   isButtonClicked = true },
            content = {
                Text(
                    text = "Send Otp",
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary_color)),
        )
    }

    if (isButtonClicked) {
        when (otpLiveData) {
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                val response = (otpLiveData as UiState.Success<InputOtpRespose>).data
                navigateToResetPassword(response.key)
                Toast.makeText(LocalContext.current, response.message, Toast.LENGTH_SHORT).show()
                isButtonClicked = false
            }
            is UiState.Error -> {
                isButtonClicked = false
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.inputOtp(otpValue)
        }
    }

}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 5,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                1.dp, when {
                    isFocused -> GreyDark
                    else -> GreyLight
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.h4,
        color = if (isFocused) {
            GreyLight
        } else {
            GreyDark
        },
        textAlign = TextAlign.Center
    )
}