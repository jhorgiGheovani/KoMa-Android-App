package com.jhorgi.registermenu.data.retrofit

import com.jhorgi.registermenu.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


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

}