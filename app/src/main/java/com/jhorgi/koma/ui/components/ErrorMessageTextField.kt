package com.jhorgi.koma.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ErrorMessageTextField(boolean: Boolean, errorText: String){
    if (boolean) {
        Text(
            text = errorText,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }
}