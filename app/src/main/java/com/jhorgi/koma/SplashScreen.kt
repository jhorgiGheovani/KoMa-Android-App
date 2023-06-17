package com.jhorgi.koma

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.navigation.AUTH_GRAPH_ROUTE
import com.jhorgi.koma.ui.navigation.SPLASH_GRAPH_ROUTE
import com.jhorgi.koma.ui.screen.login.LoginViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToHome : () -> Unit
) {
    val context = LocalContext.current
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 =  true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec =  tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(500L)

        if(viewModel.getToken(context).value?.isEmpty() == false){
            navigateToHome()
        } else {
            navController.navigate(AUTH_GRAPH_ROUTE) {
                popUpTo(SPLASH_GRAPH_ROUTE) {
                    inclusive = true
                }
            }
        }
    }
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.koma_logo)  ,
            contentDescription = "logo_koma"
        )
    }
}