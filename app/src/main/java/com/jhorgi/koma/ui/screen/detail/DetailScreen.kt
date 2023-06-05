package com.jhorgi.koma.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel= viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getItemById(id)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(name = data.item.name, id = data.item.id, onBackClick = navigateBack)
            }
            is UiState.Error -> {}
        }
    }
}


@Composable
fun DetailContent(
    name:String,
    id: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column {
        Text(text = name)
        Text(text = id)
    }
}