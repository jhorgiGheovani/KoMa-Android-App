package com.jhorgi.koma.ui.screen.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.GetUserDetailResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: MainRepository) : ViewModel(){
    private val token = MutableLiveData<String?>()
    fun setToken(token: String, context: Context) =repository.saveToken(token,context)

    fun getToken(context: Context): LiveData<String?> {
        val tokenData = repository.getToken(context)
        token.value = tokenData
        return token
    }

    private val _userDetailLiveData = MutableLiveData<UiState<GetUserDetailResponse>>()
    val userDetailLiveData: LiveData<UiState<GetUserDetailResponse>> get() = _userDetailLiveData
    fun getUserDetails(token: String){
        viewModelScope.launch {
            try {
                val response = repository.getUserDetail(token)
                _userDetailLiveData.value = response
            }catch (e:Exception){
                e.message
            }
        }
    }
}