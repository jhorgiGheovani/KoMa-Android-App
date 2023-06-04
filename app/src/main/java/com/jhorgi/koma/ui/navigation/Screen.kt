package com.jhorgi.koma.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera: Screen("camera")
    object Bookmark : Screen("bookmark")
    object Detail : Screen("home/{Id}"){
        fun createRoute(Id: String)= "home/$Id"
    }
}