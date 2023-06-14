package com.jhorgi.koma.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("accessToken")
    val accessToken: String,
)