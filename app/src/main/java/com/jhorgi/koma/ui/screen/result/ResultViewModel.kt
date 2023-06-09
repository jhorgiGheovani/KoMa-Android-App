package com.jhorgi.koma.ui.screen.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.data.remote.response.RecipeByIngredientsResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody


class ResultViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val _postPhotoLiveData = MutableLiveData<UiState<PostPhoto>>()
    val postPhotoLiveData : LiveData<UiState<PostPhoto>>  get() = _postPhotoLiveData


    fun postPhoto(photo: MultipartBody.Part): PostPhoto {
        val result = CompletableDeferred<PostPhoto>()
        viewModelScope.launch {
            val getValue = mainRepository.postPhoto(photo)
            result.complete(getValue)
        }
        return runBlocking { result.await() }
    }
    private val _resultLiveData = MutableLiveData<UiState<RecipeByIngredientsResponse>>()
    val resultLiveData : LiveData<UiState<RecipeByIngredientsResponse>> get() = _resultLiveData
    fun getRecipeByIngredient(ingredient : String) {
        viewModelScope.launch {
            try {
                val resultRecipe = mainRepository.getRecipeByIngredient(ingredient)
                _resultLiveData.value = resultRecipe
            } catch (e : Exception) {

            }

        }
    }




//    private val _recipeLiveData = MutableLiveData<UiState<RecipeByIdResponse>>()
//    val recipeLiveData : LiveData<UiState<RecipeByIdResponse>> get() = _recipeLiveData
//    fun getRecipeById(id: Int) {
//        viewModelScope.launch {
//            try {
//                val detailRecipe = repository.getRecipeById(id)
//                _recipeLiveData.value = detailRecipe
//            } catch (e : Exception) {
//
//            }
//
//        }
//    }


//    fun getRecipeById(id: Int): RecipeByIdResponse {
//        val result = CompletableDeferred<RecipeByIdResponse>()
//        viewModelScope.launch(Dispatchers.IO) {
//            val getValue = repository.getRecipeById2(id)
//            result.complete(getValue)
//        }
//        return runBlocking { result.await() }
//    }

}