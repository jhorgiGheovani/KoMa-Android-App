package com.jhorgi.koma.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2.copy(
            fontWeight = FontWeight.Normal
        ),
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp),
        textAlign = TextAlign.Start
    )
}