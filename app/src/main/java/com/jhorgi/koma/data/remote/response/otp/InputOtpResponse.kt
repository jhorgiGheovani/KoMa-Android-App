package com.jhorgi.koma.data.remote.response.otp

import com.google.gson.annotations.SerializedName

data class InputOtpRespose (

    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("key")
    val key: String,
)