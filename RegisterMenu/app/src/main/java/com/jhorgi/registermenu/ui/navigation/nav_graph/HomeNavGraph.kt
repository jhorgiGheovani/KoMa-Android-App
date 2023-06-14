package com.jhorgi.registermenu.ui.navigation.nav_graph

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jhorgi.registermenu.ui.navigation.AUTH_GRAPH_ROUTE
import com.jhorgi.registermenu.ui.navigation.HOME_GRAPH_ROUTE
import com.jhorgi.registermenu.ui.navigation.Screen
import com.jhorgi.registermenu.ui.screen.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
){
    navigation(
        startDestination = Screen.Home.route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(AUTH_GRAPH_ROUTE)
                }
            )
        }
    }

}