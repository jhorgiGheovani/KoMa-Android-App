package com.jhorgi.koma.ui.screen.inputotp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.otp.InputOtpRespose
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class InputOtpViewModel(private val repository: MainRepository) : ViewModel() {

    private val _inputOtpLiveData = MutableLiveData<UiState<InputOtpRespose>>()
    val inputOtpLiveData: LiveData<UiState<InputOtpRespose>> get() = _inputOtpLiveData

    fun inputOtp(otp: String){
        viewModelScope.launch {
            try {
                val response = repository.inputOtp(otp)
                _inputOtpLiveData.value = response
            }catch (_: Exception){

            }
        }
    }
}