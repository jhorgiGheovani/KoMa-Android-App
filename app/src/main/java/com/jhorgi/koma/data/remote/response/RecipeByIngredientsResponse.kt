package com.jhorgi.koma.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecipeByIngredientsResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("calories")
	val calories: Any? = null,
)
