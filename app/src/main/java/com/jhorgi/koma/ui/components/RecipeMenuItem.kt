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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jhorgi.koma.R


@Composable
fun RecipeMenuItem(
    name: String,
    calories: String,
    image : String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 15.dp,
                end = 10.dp,
                start = 10.dp
            )
            .border(1.dp, Color.Black,shape = RoundedCornerShape(7.dp))

    ) {
        Box(
            modifier = modifier
                .height(200.dp)
                .fillMaxWidth()

        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Recipe Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(7.dp))
            )
            //Text Informasi Kalori
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color(0x8AD9D9D9))

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_calories),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 3.dp, top = 8.dp, end = 5.dp)
                )
                Text(text = calories +"kcal", modifier = Modifier.padding(top = 5.dp, end = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.padding(5.dp),
            textAlign = TextAlign.Center,
            text = name,
            style = MaterialTheme.typography.body1,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
    }




}


