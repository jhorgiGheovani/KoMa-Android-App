package com.jhorgi.koma.ui.screen.result

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.model.DataHomeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


class PostPhotoViewModel(private val mainRepository: MainRepository) : ViewModel() {
//    private val postPhotoLiveData = MutableLiveData<UiState<PostPhoto>>()
//
//    fun postPhoto(photo : MultipartBody.Part) : LiveData<UiState<PostPhoto>> {
//        viewModelScope.launch {
//            val result  = mainRepository.postPhoto(photo)
//            postPhotoLiveData.value = result
//        }
//        return postPhotoLiveData
//    }
}