package com.jhorgi.koma.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class DetailViewModel(
    private val repository: MainRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DataHomeList>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<DataHomeList>>
        get() = _uiState

    fun getItemById(id: Int){
        viewModelScope.launch {
            _uiState.value=UiState.Loading
            _uiState.value = UiState.Success(repository.getItemById(id))
        }
    }


    fun addBookmark(bookmarkList: BookmarkList){
            repository.addBookmark(bookmarkList)
    }

    fun deleteBookmark(bookmarkList: BookmarkList){
        repository.deleteBookmark(bookmarkList)
    }

    fun isBookmarked(id: Int):Boolean = repository.isBookmarked(id)

//    private val postPhotoLiveData = MutableLiveData<UiState<PostPhoto>>()
    private val recipeLiveData = MutableLiveData<UiState<RecipeByIdResponse>>()
    fun getRecipeById(id: Int): LiveData<UiState<RecipeByIdResponse>>{
        viewModelScope.launch {
            val result = repository.getRecipeById(id)
            recipeLiveData.value = result

        }
        return recipeLiveData
    }


//    fun postPhoto(photo : MultipartBody.Part) : LiveData<UiState<PostPhoto>> {
//        viewModelScope.launch {
//            val result  = mainRepository.postPhoto(photo)
//            postPhotoLiveData.value = result
//        }
//        return postPhotoLiveData
//    }

//    fun getRecipeById(id: Int): Re
}