package com.jhorgi.registermenu.model

import com.google.gson.annotations.SerializedName

data class InputOtpRequestBody(
    @field:SerializedName("otp")
    val otp: String,
)