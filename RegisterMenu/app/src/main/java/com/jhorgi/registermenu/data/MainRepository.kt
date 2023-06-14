package com.jhorgi.registermenu.data

import android.content.Context
import com.jhorgi.registermenu.data.local.LocalDataPreference
import com.jhorgi.registermenu.data.retrofit.ApiService
import com.jhorgi.registermenu.model.*
import com.jhorgi.registermenu.ui.UiState

class MainRepository(
    private val apiService: ApiService,
) {


    suspend fun login(email: String, password: String): UiState<LoginResponse>{
        return try {
            val response = apiService.loginUser(LoginRequestBody(email,password))
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }


    suspend fun registrasi(data: RegisterRequestBody): UiState<GenericResponse>{
        return try {
            val response = apiService.registerUser(data)
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }



    suspend fun emailForgotPassword(email: String): UiState<GenericResponse>{
        return try {
            val response = apiService.emailForgotPassword(ForgotPasswordRequestBody(email))
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }

    suspend fun inputOtp(otp: String): UiState<InputOtpRespose>{
        return try {
            val response = apiService.inputOtp(InputOtpRequestBody(otp))
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }

    suspend fun resetPassword(data: ResetPasswordRequestBody): UiState<GenericResponse>{
        return try {
            val response = apiService.resetPassword(data)
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }


    fun saveToken(token: String, context: Context){
        val localDataPreference = LocalDataPreference(context)
        localDataPreference.setToken(token)
    }

    fun getToken(context: Context): String? {
        val settingPreference = LocalDataPreference(context)
        return settingPreference.getToken()
    }



    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(
            apiService: ApiService
        ): MainRepository =
            instance ?: synchronized(this) {
                MainRepository(apiService).apply {
                    instance = this
                }
            }
    }
}