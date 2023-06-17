package com.jhorgi.koma.ui.screen.change_pass

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.ChangePasswordRequestBody
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class ChangePassViewModel(private val repository: MainRepository) : ViewModel() {


    fun getToken(context: Context): String? {
        return repository.getToken(context)
    }

    private val _changePassLiveData = MutableLiveData<UiState<GenericResponse>>()
    val changePassLiveData: LiveData<UiState<GenericResponse>> get() = _changePassLiveData

    fun changePaasword(data: ChangePasswordRequestBody, token: String){
        viewModelScope.launch {
            try {
                val response = repository.changePassword(data, token)
                _changePassLiveData.value = response
            }catch (e: Exception){
                e.message
            }
        }
    }


}