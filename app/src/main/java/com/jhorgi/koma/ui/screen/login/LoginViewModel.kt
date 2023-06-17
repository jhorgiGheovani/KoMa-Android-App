package com.jhorgi.koma.ui.screen.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.login.LoginResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: MainRepository) : ViewModel() {



    private val _loginLiveData = MutableLiveData<UiState<LoginResponse>>()
    val loginLiveData: LiveData<UiState<LoginResponse>> get() = _loginLiveData

    fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = repository.login(email,password)
                _loginLiveData.value = response
            }catch (_: Exception){

            }
        }
    }

    fun setToken(token: String, context: Context) =repository.saveToken(token,context)

    private val token = MutableLiveData<String?>()
    fun getToken(context: Context): LiveData<String?> {
        val tokenData = repository.getToken(context)
        token.value = tokenData
        return token
    }
}