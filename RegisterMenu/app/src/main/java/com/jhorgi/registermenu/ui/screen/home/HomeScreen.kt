package com.jhorgi.registermenu.ui.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.registermenu.di.Injection
import com.jhorgi.registermenu.ui.ViewModelFactory

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToLogin: () -> Unit,
) {
    Log.d("HomeScreen", "HomeScreen")
    val context = LocalContext.current
    var isClicked by remember { mutableStateOf(false) }

//    if (viewModel.getToken(context).value?.isEmpty() == true) {
//        navigateToLogin()
//    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = "Home Screen")


        Row(modifier = Modifier.clickable {
            viewModel.setToken("", context)
            navigateToLogin()
            Log.d("NavigateToLogin", "NavigateToLogin")
        }) {
            Text(text = "Logout")
        }
    }
}
