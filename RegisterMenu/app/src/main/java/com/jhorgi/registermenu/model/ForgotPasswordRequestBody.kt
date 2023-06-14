package com.jhorgi.registermenu.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestBody(
    @field:SerializedName("email")
    val email: String,
)