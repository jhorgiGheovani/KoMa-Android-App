package com.jhorgi.koma.data


import android.content.Context
import com.jhorgi.koma.data.local.BookmarkDao
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.local.LocalDataPreference
import com.jhorgi.koma.data.remote.response.*
import com.jhorgi.koma.data.remote.response.forgotpassword.ForgotPasswordRequestBody
import com.jhorgi.koma.data.remote.response.forgotpassword.ResetPasswordRequestBody
import com.jhorgi.koma.data.remote.response.login.LoginRequestBody
import com.jhorgi.koma.data.remote.response.login.LoginResponse
import com.jhorgi.koma.data.remote.response.otp.InputOtpRequestBody
import com.jhorgi.koma.data.remote.response.otp.InputOtpRespose
import com.jhorgi.koma.data.remote.response.register.RegisterRequestBody
import com.jhorgi.koma.data.remote.retrofit.ApiService
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody

class MainRepository(
    private val bookmarkDao: BookmarkDao,
    private val apiService: ApiService,
    private val apiServicePredict: ApiService,
) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    suspend fun postPhoto(photo: MultipartBody.Part): UiState<PostPhoto> {
        return try {
            val response = apiServicePredict.postPhoto(photo)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }


    suspend fun getRecipeById(id: Int): UiState<RecipeByIdResponse> {
        return try {
            val response = apiService.getRecipeById(id)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }
    suspend fun getRecipeByIngredient(ingredient : String): UiState<RecipeByIngredientsResponse> {
        return try {
            val response = apiService.getRecipeByIngredient(ingredient)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }
    suspend fun getRecipeRandom(): UiState<RecipeRandomResponse> {
        return try {
            val response = apiService.getRecipeRandom()
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }


    suspend fun getRecipeById2(id: Int): RecipeByIdResponse {
        return apiService.getRecipeById(id)
    }

    fun getAllBookmark(): List<BookmarkList> {
        val result = CompletableDeferred<List<BookmarkList>>()

        coroutineScope.launch(Dispatchers.IO) {
            val getValue = bookmarkDao.getAllBookmark()
            result.complete(getValue)
        }
        return runBlocking { result.await() }
    }


    fun addBookmark(bookmarkList: BookmarkList) {
        coroutineScope.launch(Dispatchers.IO) {
            bookmarkDao.insert(bookmarkList)
        }
    }

    fun deleteBookmark(bookmarkList: BookmarkList) {
        coroutineScope.launch(Dispatchers.IO) {
            bookmarkDao.delete(bookmarkList)
        }
    }

    fun isBookmarked(id: Int): Boolean {
        val result = CompletableDeferred<Boolean>()

        coroutineScope.launch(Dispatchers.IO) {
            val isBookmarked = bookmarkDao.isBookmarked(id)
            result.complete(isBookmarked)
        }

        return runBlocking { result.await() }
    }
    suspend fun getUserDetail(token: String): UiState<GetUserDetailResponse>{
        return try {
            val response = apiService.getDetailUser(token)
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }
    suspend fun updateProfile(data: UpdateProfileRequestBody, token: String): UiState<GenericResponse>{
        return try {
            val response = apiService.updateProfile(token, data)
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }
    suspend fun changePassword(data: ChangePasswordRequestBody, token: String): UiState<GenericResponse>{
        return try {
            val response = apiService.changePassword(token, data)
            UiState.Success(response)
        }catch (e: Exception){
            UiState.Error(e.message.toString())
        }

    }
// Auth Repository
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
            bookmarkDao: BookmarkDao,
            apiService: ApiService,
            apiServicePredict: ApiService
        ): MainRepository =
            instance ?: synchronized(this) {
                MainRepository(bookmarkDao, apiService, apiServicePredict).apply {
                    instance = this
                }
            }
    }
}