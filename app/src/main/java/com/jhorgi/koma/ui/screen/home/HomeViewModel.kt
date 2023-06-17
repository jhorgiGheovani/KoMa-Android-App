package com.jhorgi.koma.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.RecipeRandomResponse
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository
): ViewModel(){

    private val _recipeLiveData = MutableLiveData<UiState<RecipeRandomResponse>>()
    val recipeLiveData : LiveData<UiState<RecipeRandomResponse>> get() = _recipeLiveData
    fun getAllRecipeRandom() {
        viewModelScope.launch {
            try {
                val detailRecipe = repository.getRecipeRandom()
                _recipeLiveData.value = detailRecipe
            } catch (_: Exception) {

            }

        }
    }
}