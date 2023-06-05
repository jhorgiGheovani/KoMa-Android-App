package com.dicoding.hellojetpackcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen (
){

    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var textStateEmail by remember { mutableStateOf("") }
        var textStatePassword by remember { mutableStateOf("") }
        Image(
            painter = painterResource(id = R.drawable.login), 
            contentDescription = "Login",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = "Email",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontSize = 14.sp
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(0.5.dp, Color.Gray),
                    shape = RoundedCornerShape(6.dp)
                ),
            value = textStateEmail,
            placeholder = { Text("Enter email")},
            colors = TextFieldDefaults
                .textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Black,
                disabledLabelColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                textStateEmail = it
            },
            trailingIcon = {
                Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.email),
                contentDescription = "Email")
            },

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontSize = 14.sp
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(0.5.dp, Color.Gray),
                    shape = RoundedCornerShape(6.dp)
                ),
            value = textStatePassword,
            placeholder = { Text("Enter password")},
            colors = TextFieldDefaults
                .textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            onValueChange = {
                textStatePassword = it
            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.eye),
                    contentDescription = "Password")
            },

            )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forgot Password ?",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp),
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.green_veg)
            ),
            onClick = {

            }
        ) {
            Text(
                color = Color.White,
                text = "Login",
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row{
            Text(
                text = "Don't have any account ?",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 14.sp
            )
            Text(
                text = "Register",
                modifier = Modifier
                    .padding(start = 3.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.green_veg),
                fontSize = 14.sp
            )
        }


    }
}

