package com.jhorgi.koma.ui.screen.regiter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.data.remote.response.login.LoginResponse
import com.jhorgi.koma.data.remote.response.register.RegisterRequestBody
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: MainRepository) : ViewModel() {


    private val _registerLiveData = MutableLiveData<UiState<GenericResponse>>()
    val registerLiveData: LiveData<UiState<GenericResponse>> get() = _registerLiveData

    fun register(data: RegisterRequestBody){
        viewModelScope.launch {
            try {
                val response = repository.registrasi(data)
                _registerLiveData.value = response
            }catch (e: Exception){
                e.message
            }
        }
    }


    private val _loginLiveData = MutableLiveData<UiState<LoginResponse>>()
    val loginLiveData: LiveData<UiState<LoginResponse>> get() = _loginLiveData

    fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = repository.login(email,password)
                _loginLiveData.value = response
            }catch (e: Exception){

            }
        }
    }
}