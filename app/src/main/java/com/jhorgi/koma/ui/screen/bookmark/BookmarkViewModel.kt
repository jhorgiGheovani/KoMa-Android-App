package com.jhorgi.koma.ui.screen.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BookmarkViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _recipeLiveData = MutableLiveData<UiState<RecipeByIdResponse>>()

        val recipeLiveData : LiveData<UiState<RecipeByIdResponse>> get() = _recipeLiveData
    fun getRecipeById(id: Int): RecipeByIdResponse {
        val result = CompletableDeferred<RecipeByIdResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            val getValue = repository.getRecipeById2(id)
            result.complete(getValue)
        }
        return runBlocking { result.await() }
    }

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

    fun getAllBookmark(): List<BookmarkList> = repository.getAllBookmark()

}