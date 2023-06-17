package com.jhorgi.koma.data.remote.retrofit

import com.jhorgi.koma.data.remote.response.*
import com.jhorgi.koma.data.remote.response.forgotpassword.ForgotPasswordRequestBody
import com.jhorgi.koma.data.remote.response.forgotpassword.ResetPasswordRequestBody
import com.jhorgi.koma.data.remote.response.login.LoginRequestBody
import com.jhorgi.koma.data.remote.response.login.LoginResponse
import com.jhorgi.koma.data.remote.response.otp.InputOtpRequestBody
import com.jhorgi.koma.data.remote.response.otp.InputOtpRespose
import com.jhorgi.koma.data.remote.response.register.RegisterRequestBody
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

//  Home
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

    @GET("/api/v1/user/detail")
    suspend fun getDetailUser(
        @Header("Authorization")Token: String,
    ): GetUserDetailResponse

    @GET("api/v1/recipe/random")
    suspend fun getRecipeRandom(): RecipeRandomResponse

// Auth
    @POST("api/v1/auth/login")
    suspend fun loginUser(
        @Body requestBody: LoginRequestBody
    ): LoginResponse

    @POST("api/v1/auth/register")
    suspend fun registerUser (
        @Body requestBody: RegisterRequestBody
    ): GenericResponse

    @POST("api/v1/user/otpgen")
    suspend fun emailForgotPassword (
        @Body requestBody: ForgotPasswordRequestBody
    ): GenericResponse

    @POST("api/v1/user/otpver")
    suspend fun inputOtp (
        @Body requestBody: InputOtpRequestBody
    ): InputOtpRespose

    @POST("api/v1/user/resetpassword")
    suspend fun resetPassword (
        @Body requestBody: ResetPasswordRequestBody
    ): GenericResponse

    @PUT("api/v1/user/detail/update")
    suspend fun updateProfile (
        @Header("Authorization")Token: String,
        @Body requestBody: UpdateProfileRequestBody
    ): GenericResponse

    @POST("api/v1/user/changepassword")
    suspend fun changePassword (
        @Header("Authorization")Token: String,
        @Body requestBody: ChangePasswordRequestBody
    ): GenericResponse

}