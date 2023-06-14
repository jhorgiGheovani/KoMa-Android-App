package com.jhorgi.koma.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository
): ViewModel(){
    private val _uiState: MutableStateFlow<UiState<List<DataHomeList>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<DataHomeList>>>

    get()=_uiState


    fun getAllItem(){
        viewModelScope.launch {
            repository.getAllItem().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
                .collect{item->
                    _uiState.value=UiState.Success(item)
                }
        }
    }
    private val _recipeLiveData = MutableLiveData<UiState<RecipeByIdResponse>>()
    val recipeLiveData : LiveData<UiState<RecipeByIdResponse>> get() = _recipeLiveData
    fun getAllRecipeById(id: Int) {
        viewModelScope.launch {
            try {
                val detailRecipe = repository.getRecipeById(id)
                _recipeLiveData.value = detailRecipe
            } catch (_: Exception) {

            }

        }
    }
}