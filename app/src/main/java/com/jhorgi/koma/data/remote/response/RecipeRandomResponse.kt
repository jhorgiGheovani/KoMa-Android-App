package com.jhorgi.koma.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecipeRandomResponse(

	@field:SerializedName("data")
	val data: List<DataItemRecipeRandom>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemRecipeRandom(

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("calories")
	val calories: Any? = null,

	@field:SerializedName("title")
	val title: String? = null
)
