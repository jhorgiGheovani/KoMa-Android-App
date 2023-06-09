package com.jhorgi.koma.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera: Screen("camera")
    object Profile: Screen("profile")
    object Bookmark : Screen("bookmark")
    object Result : Screen("camera/{ingredient}") {
        fun createRoute(ingredient : String) = "camera/$ingredient"
    }
    object Detail : Screen("home/{Id}"){
        fun createRoute(Id: String)= "home/$Id"
    }
}