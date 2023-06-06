package com.jhorgi.koma.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.R
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.RecipeMenuItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllItem()
            }
            is UiState.Success -> {
                HomeContent(theData = uiState.data, modifier = modifier, navigateToDetail = navigateToDetail)
            }
            is UiState.Error -> {}
        }
    }

}


@Composable
fun HomeContent(
    theData: List<DataHomeList>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = 30.dp,
                bottom = 30.dp
            )
        ) {
            item {
                TopContent()
            }
            items(
                theData,
                key = { it.item.id }
            ) { data ->
                RecipeMenuItem(
                    name = data.item.name,
                    calories = data.item.calorie,
                    images = data.item.photo,
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                        navigateToDetail(data.item.id.toString())
                    }
                )
            }
        }



}

@Composable
fun TopContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 15.dp,
                start = 5.dp,
                end = 5.dp
            )
    ) {
        Text(
            text = "Hello User",
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold
            ),
            color = colorResource(id = R.color.user_name_color)
        )
        Icon(
            imageVector = Icons.Sharp.AccountCircle,
            tint = colorResource(id = R.color.grey_icons),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}


