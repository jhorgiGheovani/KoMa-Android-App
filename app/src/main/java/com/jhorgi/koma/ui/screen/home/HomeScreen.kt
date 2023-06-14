package com.jhorgi.koma.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jhorgi.koma.R
import com.jhorgi.koma.data.remote.response.DataItemRecipeRandom
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.data.remote.response.RecipeByIngredientsResponse
import com.jhorgi.koma.data.remote.response.RecipeRandomResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieEmptyItem
import com.jhorgi.koma.ui.components.LottieErrorItem
import com.jhorgi.koma.ui.components.LottieLoadingItem
import com.jhorgi.koma.ui.components.RecipeMenuItem
import com.jhorgi.koma.ui.navigation.Screen
import com.jhorgi.koma.ui.screen.detail.DetailViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToDetail: (String) -> Unit,
    navigateToProfile: () -> Unit
) {
    val context = LocalContext.current
    val recipe by viewModel.recipeLiveData.observeAsState(initial = UiState.Loading)
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
            val recipeResult = (recipe as UiState.Success<RecipeRandomResponse>)
            if (recipeResult.data.data.isEmpty()) {
                Column(modifier = Modifier.padding(top = 15.dp)) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieEmptyItem(modifier = Modifier)
                        Text(
                            modifier = Modifier.padding(top = 300.dp),
                            text = "No data",
                            style = MaterialTheme.typography.body2,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                    }
                }
            } else {
                HomeContent(theData = recipeResult.data.data, modifier = modifier, navigateToDetail = navigateToDetail, navigateToProfile = navigateToProfile)
            }
        }
        is UiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieErrorItem(modifier = Modifier)
                Text(
                    modifier = Modifier.padding(top = 50.dp),
                    text = "Page Not Found",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

            }

        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllRecipeRandom()
    }

}


@Composable
fun HomeContent(
    theData: List<DataItemRecipeRandom>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    navigateToProfile: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 30.dp,
            bottom = 30.dp
        )
    ) {
        item {
            TopContent(navigateToProfile = navigateToProfile)
        }
        items(
            theData
        ) { data ->
//            Text(text = data.title.toString())
            RecipeMenuItem(
                name = data.title.toString(),
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

@Composable
fun TopContent(
    navController: NavHostController = rememberNavController(),
    navigateToProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 15.dp,
                start = 15.dp,
                end = 15.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Hello User",
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
            IconButton(
                onClick = {
                    navigateToProfile()
                }
            ) {
                Icon(
                    imageVector = Icons.Sharp.AccountCircle,
                    tint = colorResource(id = R.color.grey_icons),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

        }
        Text(
            text = "What do you want to cook today ?",
            style = MaterialTheme.typography.h1,
            color = Color.Black
        )

    }

}



