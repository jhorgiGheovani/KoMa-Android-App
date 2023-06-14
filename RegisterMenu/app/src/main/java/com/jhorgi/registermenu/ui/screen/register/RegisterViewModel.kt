package com.jhorgi.registermenu.ui.screen.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.registermenu.data.MainRepository
import com.jhorgi.registermenu.model.LoginResponse
import com.jhorgi.registermenu.model.GenericResponse
import com.jhorgi.registermenu.model.RegisterRequestBody
import com.jhorgi.registermenu.ui.UiState
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