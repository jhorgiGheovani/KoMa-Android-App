package com.jhorgi.registermenu.ui.screen.inputEmailForgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.registermenu.data.MainRepository
import com.jhorgi.registermenu.model.GenericResponse
import com.jhorgi.registermenu.ui.UiState
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