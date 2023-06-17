package com.jhorgi.koma.ui.screen.resetpass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.data.remote.response.forgotpassword.ResetPasswordRequestBody
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val repository: MainRepository) : ViewModel() {


    private val _resetPasswordLiveData = MutableLiveData<UiState<GenericResponse>>()
    val resetPasswordLiveData: LiveData<UiState<GenericResponse>> get() = _resetPasswordLiveData

    fun resetPassword(data: ResetPasswordRequestBody){
        viewModelScope.launch {
            try {
                val response = repository.resetPassword(data)
                _resetPasswordLiveData.value = response
            }catch (_: Exception){

            }
        }
    }
}