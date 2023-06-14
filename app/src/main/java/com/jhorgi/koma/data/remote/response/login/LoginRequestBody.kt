package com.jhorgi.koma.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("password")
    val password: String,
)