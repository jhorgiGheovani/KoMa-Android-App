package com.jhorgi.koma.data.remote.response

import com.google.gson.annotations.SerializedName
data class GenericResponse (
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("message")
    val message: String,
)