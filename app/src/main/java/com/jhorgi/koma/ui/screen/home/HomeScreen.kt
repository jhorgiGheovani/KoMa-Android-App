package com.jhorgi.koma.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jhorgi.koma.R
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.model.DataHomeList
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
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
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllItem()
            }
            is UiState.Success -> {
                HomeContent(theData = uiState.data, modifier = modifier, navigateToDetail = navigateToDetail, navigateToProfile = navigateToProfile)
            }
            is UiState.Error -> {}
        }
    }

}


@Composable
fun HomeContent(
    theData: List<DataHomeList>,
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
fun TopContent(
    navController: NavHostController = rememberNavController(),
    navigateToProfile: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 15.dp,
                start = 15.dp,
                end = 15.dp
            )
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
                modifier = Modifier.size(40.dp),
            )
        }

    }
}



