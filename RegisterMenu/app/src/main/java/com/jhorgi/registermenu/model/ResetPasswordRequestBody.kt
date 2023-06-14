package com.jhorgi.registermenu.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequestBody(
    @field:SerializedName("encryptKey")
    val encryptKey: String,
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("confirmPassword")
    val confirmPassword: String,
)