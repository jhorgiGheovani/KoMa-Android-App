package com.jhorgi.koma.data.remote.response.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestBody(
    @field:SerializedName("email")
    val email: String,
)