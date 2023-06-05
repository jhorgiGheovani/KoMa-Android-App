package com.jhorgi.koma.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jhorgi.koma.R


@Composable
fun RecipeMenuItem(
    name: String,
    calories: String,
    @DrawableRes images: Int,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .height(130.dp)
            .fillMaxWidth()
            .padding(
                bottom = 15.dp,
                end = 10.dp,
                start = 10.dp
            )
            .clip(shape = RoundedCornerShape(7.dp))
            .border(3.dp, Color.Black)
    ) {
        //Images Background
        Image(
            painter = painterResource(images),
            contentDescription = "images",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
        )

        //Text Informasi Kalori
        Row(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.TopEnd)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color(0x8AD9D9D9))

        ) {

            Icon(painter = painterResource(R.drawable.ic_calories), contentDescription = null)
            Text(text = calories, modifier = Modifier.padding(end = 5.dp))
        }

        //Text Nama Menu
        Text(
            text = name,
            modifier = Modifier.align(Alignment.BottomCenter),
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.White
        )

    }


}