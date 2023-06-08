package com.jhorgi.koma.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecipeByIdResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ListIngredientsItem(

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("ingredient")
	val ingredient: String,

	@field:SerializedName("qty")
	val qty: Float,

	@field:SerializedName("desc")
	val desc: String
)

data class Data(

	@field:SerializedName("instructions")
	val instructions: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("listIngredients")
	val listIngredients: List<ListIngredientsItem>,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
