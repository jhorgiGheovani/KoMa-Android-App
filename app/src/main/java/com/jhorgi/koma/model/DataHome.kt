package com.jhorgi.koma.model

import androidx.annotation.DrawableRes

data class DataHome(
    val id: Int,
    val name: String,
    val calorie: String,
    @DrawableRes val photo: Int
)