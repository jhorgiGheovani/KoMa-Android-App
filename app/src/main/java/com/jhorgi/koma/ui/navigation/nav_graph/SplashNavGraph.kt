package com.jhorgi.koma.ui.navigation.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jhorgi.koma.SplashScreen
import com.jhorgi.koma.ui.navigation.HOME_GRAPH_ROUTE
import com.jhorgi.koma.ui.navigation.SPLASH_GRAPH_ROUTE
import com.jhorgi.koma.ui.navigation.Screen

fun NavGraphBuilder.splashNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = Screen.Splash.route,
        route = SPLASH_GRAPH_ROUTE
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController,
                navigateToHome = {
                    navController.navigate(HOME_GRAPH_ROUTE) {
                        popUpTo(SPLASH_GRAPH_ROUTE) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}