package com.jhorgi.koma.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequestBody (
    @field:SerializedName("oldPassword")
    val oldPassword: String,
    @field:SerializedName("newPassword")
    val newPassword: String,
    @field:SerializedName("confirmPassword")
    val confirmPassword: String
)