package com.jhorgi.koma

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jhorgi.koma.ui.navigation.*
import com.jhorgi.koma.ui.navigation.nav_graph.authNavGraph
import com.jhorgi.koma.ui.navigation.nav_graph.homeNavGraph
import com.jhorgi.koma.ui.navigation.nav_graph.splashNavGraph
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoilApi
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
            if (
                currentRoute == Screen.Home.route || currentRoute == Screen.Bookmark.route
            ) {
                BottomAppBar(
                    backgroundColor = Color.White,
                    cutoutShape = CircleShape,
                    modifier = Modifier
                        .background(colorResource(id = R.color.white))
                        .border(0.5.dp, Color.Gray)
                ) {
                    BottomBar(navController)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (
                currentRoute == Screen.Home.route || currentRoute == Screen.Bookmark.route || currentRoute == Screen.Result.route
            ) {
                Column(
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    FloatingActionButton(
                        shape = CircleShape,
                        backgroundColor = colorResource(id = R.color.primary_color),
                        contentColor = Color.White,
                        modifier = modifier
                            .size(60.dp)
                            .border(2.dp, Color.Green, CircleShape),
                        onClick = {
                            Screen.Camera.route.let {
                                navController.navigate(it) {
                                    popUpTo(Screen.Camera.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                                navController.popBackStack()
                            }
                            Screen.Camera.route.let {navController.navigate(it)}
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SPLASH_GRAPH_ROUTE,
            route = ROOT_GRAPH_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            homeNavGraph(navController = navController)
            authNavGraph(navController = navController)
            splashNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = Color.Black,
        elevation = 0.dp
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
                title = "",
                icon = ImageVector.vectorResource(id = R.drawable.gallery),
                screen = Screen.Camera
            ),
            NavigationItem(
                title = stringResource(R.string.menu_bookmark),
                icon = ImageVector.vectorResource(id = R.drawable.ic_bookmark_border),
                screen = Screen.Bookmark,
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
                    Text(
                        item.title,
                        style = MaterialTheme.typography.body2
                    )
                },

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
val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")


