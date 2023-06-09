package com.jhorgi.koma.data.remote.retrofit

import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.data.remote.response.RecipeByIngredientsResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

//    @Headers("Content-Type: multipart/form-data")
    @Multipart
    @POST("/")
    suspend fun postPhoto(
        @Part file: MultipartBody.Part
    ) : PostPhoto

    @GET("api/v1/recipe/{id}")
    suspend fun getRecipeById(
        @Path("id") id: Int
    ): RecipeByIdResponse

    @GET("api/v1/recipe")
    suspend fun getRecipeByIngredient(
        @Query("ingredient") ingredient : String
    ): RecipeByIngredientsResponse

}