package com.jhorgi.koma.ui.screen.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.data.remote.response.RecipeByIngredientsResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.*
import okhttp3.MultipartBody


class ResultViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val _postPhotoLiveData = MutableLiveData<UiState<PostPhoto>>()
    val postPhotoLiveData: LiveData<UiState<PostPhoto>> get() = _postPhotoLiveData
    fun postPhoto(photo: MultipartBody.Part) {
        val result = CompletableDeferred<UiState<PostPhoto>>()
        viewModelScope.launch {
            val resultRecipe = mainRepository.postPhoto(photo)
            result.complete(resultRecipe)
            _postPhotoLiveData.value = runBlocking { result.await() }
        }
    }
    private val _resultLiveData = MutableLiveData<UiState<RecipeByIngredientsResponse>>()
    val resultLiveData: LiveData<UiState<RecipeByIngredientsResponse>> get() = _resultLiveData
    fun getRecipeByIngredient(ingredient: String) {
        viewModelScope.launch {
            try {
                val resultRecipe = mainRepository.getRecipeByIngredient(ingredient)
                _resultLiveData.value = resultRecipe
            } catch (_: Exception) {

            }
        }
    }
}