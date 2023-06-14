package com.jhorgi.koma.ui.screen.result

import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val postPhotoLiveData : LiveData<UiState<PostPhoto>>  get() = _postPhotoLiveData
    fun postPhoto(photo: MultipartBody.Part){
        val result = CompletableDeferred<UiState<PostPhoto>>()
        viewModelScope.launch{
            val resultRecipe = mainRepository.postPhoto(photo)
            result.complete(resultRecipe)
            _postPhotoLiveData.value = runBlocking { result.await() }
        }
    }
//
//    var open = MutableLiveData<Boolean>()
//    fun startThread(photo: MultipartBody.Part) {
//        viewModelScope.launch {
//
//            withContext(Dispatchers.Default) {
//                // Do the background work here
//                // I'm adding delay
////                delay(3000)
//                postPhoto(photo)
//            }
//
//            closeDialog()
//        }
//    }
//
////    private fun closeDialog() {
////        open.value = false
////    }
//    fun postPhoto(photo: MultipartBody.Part){
//        val result = CompletableDeferred<PostPhoto>()
//        viewModelScope.launch(Dispatchers.IO) {
//            val getValue = mainRepository.postPhoto(photo)
//        }
//        return runBlocking { result.await() }
//    }


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