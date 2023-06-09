package com.jhorgi.koma.ui.screen.result

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jhorgi.koma.data.remote.response.DataItem
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.data.remote.response.RecipeByIngredientsResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.RecipeMenuItem
import com.jhorgi.koma.ui.screen.detail.DetailContent
import com.jhorgi.koma.ui.screen.detail.shimmerEffect
import com.jhorgi.koma.ui.screen.home.HomeContent
import com.jhorgi.koma.ui.screen.home.TopContent
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(
    ingredient : String,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToDetail: (String) -> Unit,
) {

    val context = LocalContext.current
    val recipe by viewModel.resultLiveData.observeAsState(initial = UiState.Loading)
    when (recipe) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp)
                    .shimmerEffect(),

                )
            Spacer(modifier = Modifier.width(16.dp))
            Toast.makeText(context, ingredient, Toast.LENGTH_LONG).show()
        }
        is UiState.Success -> {
            val recipeResult = (recipe as UiState.Success<RecipeByIngredientsResponse>).data
            ResultContent(theData = recipeResult.data, navigateToDetail = navigateToDetail )
        }
        is UiState.Error -> {
            // Show error state or handle the error
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getRecipeByIngredient(ingredient)
    }
}

@Composable
fun ResultContent(
    theData: List<DataItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 30.dp,
            bottom = 30.dp
        )
    ) {
        items(
            theData
        ) { data ->
           Text(text = data.title)
        }
    }


}
