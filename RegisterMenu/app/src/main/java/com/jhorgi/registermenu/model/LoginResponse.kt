package com.jhorgi.registermenu.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("accessToken")
    val accessToken: String,
)