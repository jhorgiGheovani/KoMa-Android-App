package com.jhorgi.koma.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetUserDetailResponse(

	@field:SerializedName("data")
	val data: DataDetail? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataDetail(

	@field:SerializedName("photoProfile")
	val photoProfile: Any? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = "-",

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("weight")
	val weight: Int? = 0,

	@field:SerializedName("calories")
	val calories: String? = "-",

	@field:SerializedName("email")
	val email: String? = "-",

	@field:SerializedName("height")
	val height: Int? = 0
)
