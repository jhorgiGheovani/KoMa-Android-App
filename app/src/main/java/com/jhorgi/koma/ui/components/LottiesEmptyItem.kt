package com.jhorgi.koma.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieEmptyItem(
    modifier: Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url("https://assets1.lottiefiles.com/temp/lf20_Celp8h.json"))
    LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever )

}