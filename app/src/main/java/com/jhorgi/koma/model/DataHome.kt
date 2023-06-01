package com.jhorgi.koma.model

import androidx.annotation.DrawableRes

data class DataHome(
    val id: String,
    val name: String,
    val calorie: String,
    @DrawableRes val photo: Int
)