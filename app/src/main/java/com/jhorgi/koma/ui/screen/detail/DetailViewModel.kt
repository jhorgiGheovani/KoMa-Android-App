package com.jhorgi.koma.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MainRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<RecipeByIdResponse>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<RecipeByIdResponse>> get() = _uiState




    fun addBookmark(bookmarkList: BookmarkList){
            repository.addBookmark(bookmarkList)
    }

    fun deleteBookmark(bookmarkList: BookmarkList){
        repository.deleteBookmark(bookmarkList)
    }

    fun isBookmarked(id: Int):Boolean = repository.isBookmarked(id)

//    private val postPhotoLiveData = MutableLiveData<UiState<PostPhoto>>()
    private val _recipeLiveData = MutableLiveData<UiState<RecipeByIdResponse>>()
    val recipeLiveData : LiveData<UiState<RecipeByIdResponse>> get() = _recipeLiveData
    fun getRecipeById(id: Int) {
        viewModelScope.launch {
            try {
                val detailRecipe = repository.getRecipeById(id)
                _recipeLiveData.value = detailRecipe
            } catch (e : Exception) {

            }

        }
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