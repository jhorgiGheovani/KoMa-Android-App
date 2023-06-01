package com.jhorgi.koma.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MainRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DataHomeList>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataHomeList>>
        get() = _uiState

    fun getItemById(id: String){
        viewModelScope.launch {
            _uiState.value=UiState.Loading
            _uiState.value = UiState.Success(repository.getItemById(id))
        }
    }
}