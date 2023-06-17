package com.jhorgi.koma.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequestBody(

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
