package com.jhorgi.koma.ui.screen.regiter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.data.remote.response.login.LoginResponse
import com.jhorgi.koma.data.remote.response.register.RegisterRequestBody
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class RegisterViewModel(private val repository: MainRepository) : ViewModel() {


    private val _registerLiveData = MutableLiveData<UiState<GenericResponse>>()
    val registerLiveData: LiveData<UiState<GenericResponse>> get() = _registerLiveData

    fun register(data: RegisterRequestBody){
        val regis = CompletableDeferred<UiState<GenericResponse>>()
        viewModelScope.launch {
            val response = repository.registrasi(data)
            regis.complete(response)
            _registerLiveData.value = runBlocking { regis.await() }
        }
    }

//    private val _postPhotoLiveData = MutableLiveData<UiState<PostPhoto>>()
//    val postPhotoLiveData: LiveData<UiState<PostPhoto>> get() = _postPhotoLiveData
//    fun postPhoto(photo: MultipartBody.Part) {
//        val result = CompletableDeferred<UiState<PostPhoto>>()
//        viewModelScope.launch {
//            val resultRecipe = mainRepository.postPhoto(photo)
//            result.complete(resultRecipe)
//            _postPhotoLiveData.value = runBlocking { result.await() }
//        }
//    }

    private val _loginLiveData = MutableLiveData<UiState<LoginResponse>>()

    fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = repository.login(email,password)
                _loginLiveData.value = response
            }catch (_: Exception){

            }
        }
    }
}