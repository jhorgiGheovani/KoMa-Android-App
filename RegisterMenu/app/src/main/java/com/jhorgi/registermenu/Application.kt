package com.jhorgi.registermenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jhorgi.registermenu.ui.navigation.AUTH_GRAPH_ROUTE
import com.jhorgi.registermenu.ui.navigation.ROOT_GRAPH_ROUTE
import com.jhorgi.registermenu.ui.navigation.nav_graph.authNavGraph
import com.jhorgi.registermenu.ui.navigation.nav_graph.homeNavGraph


@Composable
fun Application123(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = AUTH_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        homeNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}