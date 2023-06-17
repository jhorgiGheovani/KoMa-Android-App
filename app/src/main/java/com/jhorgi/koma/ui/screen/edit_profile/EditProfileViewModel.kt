package com.jhorgi.koma.ui.screen.edit_profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.data.remote.response.UpdateProfileRequestBody
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: MainRepository) : ViewModel() {

    private val _editProfileLiveData = MutableLiveData<UiState<GenericResponse>>()
    val editProfileLiveData: LiveData<UiState<GenericResponse>> get() = _editProfileLiveData

    fun upadateProfile(data: UpdateProfileRequestBody, token: String){
        viewModelScope.launch {
            try {
                val response = repository.updateProfile(data, token)
                _editProfileLiveData.value = response
            }catch (e: Exception){
                e.message
            }
        }
    }

    private val token = MutableLiveData<String?>()
    fun getToken(context: Context): LiveData<String?> {
        val tokenData = repository.getToken(context)
        token.value = tokenData
        return token
    }
}