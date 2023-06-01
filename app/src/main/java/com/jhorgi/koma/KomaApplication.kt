package com.jhorgi.koma

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jhorgi.koma.ui.navigation.NavigationItem
import com.jhorgi.koma.ui.navigation.Screen
import com.jhorgi.koma.ui.screen.bookmark.BookmarkScreen
import com.jhorgi.koma.ui.screen.camera.CameraScreen
import com.jhorgi.koma.ui.screen.detail.DetailScreen
import com.jhorgi.koma.ui.screen.home.HomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@Composable
fun KomaApplication(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
//                BottomBar(navController)
                BottomAppBar(
                    backgroundColor = Color.White,
                    cutoutShape = CircleShape,
                    modifier = Modifier.background(colorResource(id = R.color.light_grey_super))
                ) {
                    BottomBar(navController)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (currentRoute != Screen.Detail.route) {
                FloatingActionButton(
                    shape = CircleShape,
                    backgroundColor = colorResource(id = R.color.primary_color),
                    contentColor = Color.White,
                    modifier = modifier.size(60.dp).border(2.dp, Color.Green, CircleShape),
                    onClick = {
                        Screen.Camera.route.let {
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                        Screen.Camera.route.let { navController.navigate(it) }

                    }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                            contentDescription = null
                        )
                        Text(text = "Take", fontSize = 9.sp)
                    }


                }

            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { Id ->
                        navController.navigate(Screen.Detail.createRoute(Id))
                    }
                )
            }
            composable(Screen.Camera.route) {
                CameraScreen()
            }
            composable(Screen.Bookmark.route) {
                BookmarkScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("Id") { type = NavType.StringType })
            ) {
                val navid = it.arguments?.getString("Id")
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

}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = Color.Black,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = ImageVector.vectorResource(id = R.drawable.ic_home),
                screen = Screen.Home,
            ),
            NavigationItem(
                title = stringResource(R.string.menu_bookmark),
                icon = ImageVector.vectorResource(id = R.drawable.ic_bookmark),
                screen = Screen.Bookmark
            ),
        )

        navigationItems.map { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                    )
                },
                label = {
                    Text(item.title)
                },
//                selected = item.title== navigationItems[0].title,
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }


    }
}