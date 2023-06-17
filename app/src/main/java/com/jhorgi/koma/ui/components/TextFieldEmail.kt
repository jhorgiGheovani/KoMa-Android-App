package com.jhorgi.koma.ui.components


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jhorgi.koma.R


@Composable
fun TextFieldEmail(textInput: String, onTextInputChange: (String) -> Unit, placeholder: String="Enter Email") {
    TextField(
        value = textInput,
        onValueChange = onTextInputChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.caption) },
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
}

@Composable
fun EmailErrorMessage(isEmailValid: Boolean){
    if (isEmailValid) {
        Text(
            text = "Email Format Not Valid",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }
}