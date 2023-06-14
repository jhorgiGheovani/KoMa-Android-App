package com.jhorgi.koma.ui.screen.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhorgi.koma.data.MainRepository

class ProfileViewModel(private val repository: MainRepository) : ViewModel(){

//    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
//    val viewState: Flow<ViewState> = _viewState.asStateFlow()


    private val token = MutableLiveData<String?>()
    fun setToken(token: String, context: Context) =repository.saveToken(token,context)

    fun getToken(context: Context): LiveData<String?> {
        val tokenData = repository.getToken(context)
        token.value = tokenData
        return token
    }
}