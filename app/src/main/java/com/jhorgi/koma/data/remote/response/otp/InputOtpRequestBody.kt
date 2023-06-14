package com.jhorgi.koma.data.remote.response.otp

import com.google.gson.annotations.SerializedName

data class InputOtpRequestBody(
    @field:SerializedName("otp")
    val otp: String,
)