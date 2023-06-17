package com.jhorgi.koma.ui.screen.result

import com.jhorgi.koma.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.data.remote.response.DataItem
import com.jhorgi.koma.data.remote.response.RecipeByIngredientsResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieEmptyItem
import com.jhorgi.koma.ui.components.LottieLoadingItem
import com.jhorgi.koma.ui.components.RecipeMenuItem

@Composable
fun ResultScreen(
    ingredient : String,
    viewModel: ResultViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToDetail: (String) -> Unit,
    navigateBack : () -> Unit

) {
    val recipe by viewModel.resultLiveData.observeAsState(initial = UiState.Loading)
    when (recipe) {
        is UiState.Loading -> {
           Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieLoadingItem(
                    modifier = Modifier
                )
                Text(
                    text = "Please wait...",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
        is UiState.Success -> {
            val recipeResult = (recipe as UiState.Success<RecipeByIngredientsResponse>).data
            Column{
                Row(
                    modifier = Modifier.padding(top = 25.dp, start = 10.dp, end = 10.dp),
                ) {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
                            contentDescription = "arrow_left",
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = "Best recomendation recipe of $ingredient",
                        style = MaterialTheme.typography.h1,
                        color = Color.Black
                    )
                }

                if (recipeResult.data.isEmpty()) {
                    Column(modifier = Modifier.padding(top = 15.dp)) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LottieEmptyItem(modifier = Modifier)
                            Text(
                                modifier = Modifier.padding(top = 300.dp),
                                text = "No recipe from $ingredient",
                                style = MaterialTheme.typography.body2,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )

                        }
                    }

                } else {
                    ResultContent(
                        theData = recipeResult.data,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
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
            RecipeMenuItem(
                name = data.title,
                calories = data.calories.toString(),
                image = data.images[0],
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navigateToDetail(data.id.toString())
                    }
            )

        }
    }
}
