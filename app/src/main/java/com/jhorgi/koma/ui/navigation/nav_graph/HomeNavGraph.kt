@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jhorgi.koma.ui.navigation.nav_graph

import androidx.activity.compose.BackHandler
import androidx.navigation.*
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jhorgi.koma.ui.navigation.AUTH_GRAPH_ROUTE
import com.jhorgi.koma.ui.navigation.HOME_GRAPH_ROUTE
import com.jhorgi.koma.ui.navigation.Screen
import com.jhorgi.koma.ui.screen.bookmark.BookmarkScreen
import com.jhorgi.koma.ui.screen.camera.CameraContent
import com.jhorgi.koma.ui.screen.detail.DetailScreen
import com.jhorgi.koma.ui.screen.home.HomeScreen
import com.jhorgi.koma.ui.screen.profile.ProfileScreen
import com.jhorgi.koma.ui.screen.result.ResultScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalPermissionsApi::class, ExperimentalCoilApi::class)
fun NavGraphBuilder.homeNavGraph(
    navController: NavController
){
    navigation(
        startDestination = Screen.Home.route,
        route = HOME_GRAPH_ROUTE
    ) {
//        composable(Screen.Home.route) {
//            HomeScreen(
//                navigateToLogin = {
//                    navController.popBackStack()
//                    navController.navigate(AUTH_GRAPH_ROUTE)
//                }
//            )
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { Id ->
                        navController.navigate(Screen.Detail.createRoute(Id))
                    },
                    navigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    }
                )

            }
            composable(
                route = Screen.Result.route,
                arguments = listOf(navArgument("ingredient") {type = NavType.StringType})
            ) {
                val naving = it.arguments?.getString("ingredient")
                ResultScreen(
                    ingredient = naving!!,
                    navigateToDetail = { Id ->
                        navController.navigate(Screen.Detail.createRoute(Id))
                    },
                    navigateBack = {
                        navController.popBackStack()
                        navController.navigateUp()
                    }
                )
                BackHandler {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                }
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateToLogin = {
                        navController.popBackStack()
                        navController.navigateUp()
                        navController.navigate(AUTH_GRAPH_ROUTE) {
                            popUpTo(AUTH_GRAPH_ROUTE) {
                                inclusive = true
                            }
                        }

                    }
                )
            }
            composable(Screen.Camera.route) {
                CameraContent(
                    navigationToResult = { ingredient ->
                        navController.navigate(Screen.Result.createRoute(ingredient))
                    },
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Bookmark.route) {
                BookmarkScreen(
                    navigateToDetail = { Id ->
                        navController.navigate(Screen.Detail.createRoute(Id))
                    })
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("Id") { type = NavType.IntType })
            ) {
                val navid = it.arguments?.getInt("Id")
                if (navid != null) {
                    DetailScreen(
                        id = navid,
                        navigateBack = {
                            navController.navigateUp()
                        },
                    )
                }
            }
        }
    }

//}


