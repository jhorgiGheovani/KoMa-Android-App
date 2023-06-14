package com.jhorgi.koma.data.remote.response.register

import com.google.gson.annotations.SerializedName

data class RegisterRequestBody(
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("fullName")
    val fullName: String,
    @field:SerializedName("gender")
    val gender: String,
)