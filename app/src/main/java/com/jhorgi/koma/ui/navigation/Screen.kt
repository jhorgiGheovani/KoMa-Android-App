package com.jhorgi.koma.ui.navigation


const val ROOT_GRAPH_ROUTE = "root_route"
const val AUTH_GRAPH_ROUTE = "auth_route"
const val HOME_GRAPH_ROUTE = "home_route"

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera: Screen("camera")
    object Profile: Screen("profile")
    object Bookmark : Screen("bookmark")
    object Loading : Screen("loading")
    object Result : Screen("camera/{ingredient}") {
        fun createRoute(ingredient : String) = "camera/$ingredient"
    }
    object Detail : Screen("home/{Id}"){
        fun createRoute(Id: String)= "home/$Id"
    }

//    Auth
    object Login : Screen("login")
    object Register: Screen("register")
    object InputOtp: Screen("inputOtp")
    object EmailForgotPassword: Screen("emailForgotPassword")
    object ResetPassword: Screen("inputOtp/{key}"){
        fun createRoute(key : String) = "inputOtp/$key"
    }
}