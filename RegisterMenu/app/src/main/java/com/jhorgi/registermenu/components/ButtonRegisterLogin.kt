package com.jhorgi.registermenu.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jhorgi.registermenu.R


@Composable
fun ButtonRegisterLogin(text: String) {
    Button(
        onClick = { /*TODO*/ },
        content = {
            Text(
                text = text,
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
    )
}

//private fun isValueEmpty(text: String): Boolean{
//    var check = false
//    if(text!=""){
//        check = true
//    }
//
//    return check
//}