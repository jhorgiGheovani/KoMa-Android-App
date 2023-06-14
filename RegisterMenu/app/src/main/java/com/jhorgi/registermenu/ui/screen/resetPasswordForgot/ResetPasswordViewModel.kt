package com.jhorgi.registermenu.ui.screen.resetPasswordForgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.registermenu.data.MainRepository
import com.jhorgi.registermenu.model.GenericResponse
import com.jhorgi.registermenu.model.LoginResponse
import com.jhorgi.registermenu.model.ResetPasswordRequestBody
import com.jhorgi.registermenu.ui.UiState
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val repository: MainRepository) : ViewModel() {


    private val _resetPasswordLiveData = MutableLiveData<UiState<GenericResponse>>()
    val resetPasswordLiveData: LiveData<UiState<GenericResponse>> get() = _resetPasswordLiveData

    fun resetPassword(data: ResetPasswordRequestBody){
        viewModelScope.launch {
            try {
                val response = repository.resetPassword(data)
                _resetPasswordLiveData.value = response
            }catch (e: Exception){

            }
        }
    }
}