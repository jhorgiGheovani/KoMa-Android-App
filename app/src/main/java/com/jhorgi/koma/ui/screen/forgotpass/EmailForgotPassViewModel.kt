package com.jhorgi.koma.ui.screen.forgotpass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class EmailForgotPasswordViewModel(private val repository: MainRepository) : ViewModel() {

    private val _emailForgotPasswordLiveData = MutableLiveData<UiState<GenericResponse>>()
    val emailForgotPasswordLiveData: LiveData<UiState<GenericResponse>> get() = _emailForgotPasswordLiveData

    fun requestOtp(email: String){
        viewModelScope.launch {
            try {
                val response = repository.emailForgotPassword(email)
                _emailForgotPasswordLiveData.value = response
            }catch (e: Exception){

            }
        }
    }
}